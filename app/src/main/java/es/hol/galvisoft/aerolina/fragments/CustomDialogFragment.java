package es.hol.galvisoft.aerolina.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.activities.FindActivity;
import es.hol.galvisoft.aerolina.adapters.AirportAdapter;
import es.hol.galvisoft.aerolina.model.Airport;
import es.hol.galvisoft.aerolina.services.HttpManager;

/**
 * Creado por Juan Carlos el dia 07/12/2014.
 */
public class CustomDialogFragment extends DialogFragment implements TextWatcher {
    public static final String TYPE = "type";
    public static final int ORIGIN_CITY = 1;
    public static final int DESTINY_CITY = 2;
    public static final int DATE_ROUND = 3;
    public static final int DATE_TRIP = 4;
    private ListView lvAirports;
    private int type;
    private List<Airport> airports;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.type = getArguments().getInt(TYPE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_cities, null);
        switch (type) {
            case DATE_ROUND: {
                view.findViewById(R.id.filter_city).setVisibility(View.GONE);
                view.findViewById(R.id.cities_list).setVisibility(View.GONE);
                TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
                ImageView icon = (ImageView) view.findViewById(R.id.subtitle_icon);
                icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_find));
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                subtitle.setText(R.string.select_round_date);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((FindActivity) getActivity()).setDateRound(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                        dismiss();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                break;
            }
            case DATE_TRIP: {
                view.findViewById(R.id.filter_city).setVisibility(View.GONE);
                view.findViewById(R.id.cities_list).setVisibility(View.GONE);
                TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
                ImageView icon = (ImageView) view.findViewById(R.id.subtitle_icon);
                icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_find));
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                subtitle.setText(R.string.select_trip_date);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((FindActivity) getActivity()).setDateTrip(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                        dismiss();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                break;
            }
        }
        if (type == ORIGIN_CITY || type == DESTINY_CITY) {
            if (HttpManager.getInstance().noNetworkConnection(getActivity())) {
                //mostrara error en un futuro
            }
            view.findViewById(R.id.date_picker).setVisibility(View.GONE);
            TextView textView = (TextView) view.findViewById(R.id.filter_city);
            textView.addTextChangedListener(this);
            lvAirports = (ListView) view.findViewById(R.id.cities_list);
            filterCities(null);
            TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
            if (type == ORIGIN_CITY) {
                subtitle.setText(R.string.select_origin_city);
            } else {
                subtitle.setText(R.string.select_destiny_city);
            }
            ImageView icon = (ImageView) view.findViewById(R.id.subtitle_icon);
            icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_find));
        }
        builder.setView(view);
        return builder.create();
    }

    public void onItemClick(long id, String airport) {
        switch (type) {
            case ORIGIN_CITY: {
                ((FindActivity) getActivity()).setOriginCity(id, airport);
                dismiss();
                break;
            }
            case DESTINY_CITY: {
                ((FindActivity) getActivity()).setDestinyCity(id, airport);
                dismiss();
                break;
            }
        }
        dismiss();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filterCities(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void filterCities(String filter) {
        if (filter == null) {
            AirportAdapter airportAdapter = new AirportAdapter(this, R.layout.item_airports, airports);
            lvAirports.setAdapter(airportAdapter);
            lvAirports.setOnItemClickListener(airportAdapter);
        } else {
            List<Airport> filterAirports = new ArrayList<>();
            for (Airport a : airports) {
                if (a.getIata().toLowerCase().contains(filter.toLowerCase())
                        || a.getCity().toLowerCase().contains(filter.toLowerCase())
                        || a.getName().toLowerCase().contains(filter.toLowerCase())) {
                    filterAirports.add(a);
                }
            }
            AirportAdapter airportAdapter = new AirportAdapter(this, R.layout.item_airports, filterAirports);
            lvAirports.setAdapter(airportAdapter);
            lvAirports.setOnItemClickListener(airportAdapter);
        }
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }
}