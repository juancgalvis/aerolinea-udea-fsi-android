package es.hol.galvisoft.aerolina.fragments.find;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.activities.FindActivity;
import es.hol.galvisoft.aerolina.activities.UserActivity;
import es.hol.galvisoft.aerolina.data.AirportDataManager;
import es.hol.galvisoft.aerolina.model.Airport;
import es.hol.galvisoft.aerolina.utils.TimeUtil;

/**
 * Creado por Juan Carlos el dia 31/05/2015.
 */
public class ReserveFragment extends Fragment {
    private FindActivity context;
    private double percentages[] = new double[]{0.6, 0.8, 1};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_reserve, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().isAuthenticated()) {
            ((Button) view.findViewById(R.id.bt_reserve)).setText("Reservar");
        } else {
            ((Button) view.findViewById(R.id.bt_reserve)).setText("Iniciar sesión / Registrarse");
        }
        view.findViewById(R.id.bt_reserve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().isAuthenticated()) {
                    guardarReserva();
                } else {
                    startActivityForResult(new Intent(context, UserActivity.class), 123);
                }
            }
        });
        AirportDataManager airportDataManager = new AirportDataManager(context);
        Airport originAirport = airportDataManager.getAirport(context.getRoundAirport());
        ((TextView) view.findViewById(R.id.origin)).setText(originAirport.getIata() + "-" + originAirport.getName() + ", " + originAirport.getCity());
        view.findViewById(R.id.origin).setSelected(true);
        Airport destinationAirport = airportDataManager.getAirport(context.getTripAirport());
        ((TextView) view.findViewById(R.id.destiny)).setText(destinationAirport.getIata() + "-" + destinationAirport.getName() + ", " + destinationAirport.getCity());
        view.findViewById(R.id.destiny).setSelected(true);
        ((TextView) view.findViewById(R.id.date_round)).setText(TimeUtil.DATE_FORMATTER.format(context.getRoundFlight().getDate("departureDate")));
        ((TextView) view.findViewById(R.id.time_round)).setText(TimeUtil.HOUR_MILITAR_FORMATTER.format(context.getRoundFlight().getDate("departureDate")));
        ((TextView) view.findViewById(R.id.time_trip)).setText(TimeUtil.HOUR_MILITAR_FORMATTER.format(context.getRoundFlight().getDate("arrivalDate")));
        ((TextView) view.findViewById(R.id.price_round)).setText(Double.toString(context.getRoundFlight().getDouble("price") * percentages[context.getClassType()]));

        if (context.isRoundTrip()) {
            ((TextView) view.findViewById(R.id.date_trip)).setText(TimeUtil.DATE_FORMATTER.format(context.getTripFlight().getDate("departureDate")));
            ((TextView) view.findViewById(R.id.time_round2)).setText(TimeUtil.HOUR_MILITAR_FORMATTER.format(context.getTripFlight().getDate("departureDate")));
            ((TextView) view.findViewById(R.id.time_trip2)).setText(TimeUtil.HOUR_MILITAR_FORMATTER.format(context.getTripFlight().getDate("arrivalDate")));
            ((TextView) view.findViewById(R.id.price_trip)).setText(Double.toString(context.getTripFlight().getDouble("price") * percentages[context.getClassType()]));

            ((TextView) view.findViewById(R.id.price_total)).setText(
                    Double.toString((context.getTripFlight().getDouble("price")
                            + context.getRoundFlight().getDouble("price")) * percentages[context.getClassType()]));
        } else {
            view.findViewById(R.id.vuelo_regreso).setVisibility(View.GONE);

            ((TextView) view.findViewById(R.id.price_total)).setText(Double.toString(context.getRoundFlight().getDouble("price") * percentages[context.getClassType()]));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = (FindActivity) activity;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            guardarReserva();
        }
    }

    private void guardarReserva() {
        ParseObject tripReservation = null;
        if (context.isRoundTrip()) {
            tripReservation = ParseObject.create("Reservation");
            tripReservation.put("checkin", false);
            tripReservation.put("price", context.getTripFlight().getDouble("price") * percentages[context.getClassType()]);
            ParseRelation<ParseObject> flightRelation = tripReservation.getRelation("flight");
            flightRelation.add(context.getTripFlight());
            ParseRelation<ParseObject> userRelation = tripReservation.getRelation("user");
            userRelation.add(ParseUser.getCurrentUser());
        }

        final ParseObject roundReservation = ParseObject.create("Reservation");
        roundReservation.put("checkin", false);
        roundReservation.put("price", context.getRoundFlight().getDouble("price") * percentages[context.getClassType()]);
        roundReservation.put("flight", context.getRoundFlight());
        roundReservation.put("user", ParseUser.getCurrentUser());
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("ClassType");
        parseQuery.whereEqualTo("code", context.getClassType());
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Guardando su reserva de ida", "Por favor espere...", true, false);
        final ParseObject finalTripReservation = tripReservation;
        parseQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject classType, ParseException e) {
                roundReservation.put("classType", classType);
                if (context.isRoundTrip() && finalTripReservation != null) {
                    finalTripReservation.put("classType", classType);
                }
                roundReservation.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        try {
                            progressDialog.dismiss();
                        } catch (Exception ignored) {
                        }
                        if (e == null) {
                            Toast.makeText(context, "Su reserva de ida ha sido guardada exitosamente", Toast.LENGTH_LONG).show();
                            if (context.isRoundTrip() && finalTripReservation != null) {
                                final ProgressDialog progressDialog = ProgressDialog.show(context, "Guardando su reserva de regreso", "Por favor espere...", true, false);
                                finalTripReservation.put("checkin", false);
                                finalTripReservation.put("price", context.getTripFlight().getDouble("price") * percentages[context.getClassType()]);
                                finalTripReservation.put("flight", context.getTripFlight());
                                finalTripReservation.put("user", ParseUser.getCurrentUser());
                                finalTripReservation.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        try {
                                            progressDialog.dismiss();
                                        } catch (Exception ignored) {
                                        }
                                        if (e == null) {
                                            Toast.makeText(context, "Su reserva de regreso ha sido guardada exitosamente", Toast.LENGTH_LONG).show();
                                            context.finish();
                                        } else {
                                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else{
                                context.finish();
                            }
                        } else {
                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
