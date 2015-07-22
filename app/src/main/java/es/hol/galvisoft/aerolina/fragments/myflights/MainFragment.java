package es.hol.galvisoft.aerolina.fragments.myflights;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.activities.MyFlightsActivity;
import es.hol.galvisoft.aerolina.adapters.ReservationAdapter;

/**
 * Creado por Juan Carlos el dia 31/05/2015.
 */
public class MainFragment extends Fragment implements AdapterView.OnItemClickListener {
    private List<ParseObject> reservations;
    private MyFlightsActivity context;
    private ListView lvReservations;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (MyFlightsActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myflights_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureUI();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ParseObject reservation = (ParseObject) lvReservations.getAdapter().getItem(position);
        if(reservation.getBoolean("checkin")){
            Toast.makeText(context, "Ya realizaste el checkin de este vuelo", Toast.LENGTH_SHORT).show();
            return;
        }
        context.setReservation(reservation);
        context.setFragment(MyFlightsActivity.SELECT_SEAT_FRAGMENT);
    }

    private void configureUI() {
        lvReservations = (ListView) context.findViewById(R.id.list_reservations);
        lvReservations.setOnItemClickListener(this);
        ParseQuery<ParseObject> queryReservations = ParseQuery.getQuery("Reservation");
        queryReservations.whereEqualTo("user", ParseUser.getCurrentUser());
        queryReservations.include("flight");
        queryReservations.include("flight.origin");
        queryReservations.include("flight.destination");
        queryReservations.include("seat");
        queryReservations.include("classType");
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Consultando vuelos y reservas", "Por favor espere...", true, false);
        queryReservations.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> reservations, ParseException e) {
                if (e == null) {
                    if (reservations.isEmpty()) {
                        Toast.makeText(getActivity(), "No tienes vuelos, animate a viajar", Toast.LENGTH_LONG).show();
                    } else {
                        lvReservations.setAdapter(new ReservationAdapter(context, R.layout.item_reservations, reservations));
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                try {
                    progressDialog.dismiss();
                } catch (Exception ignored) {
                }
            }
        });
    }

    public boolean next() {
        return true;
    }
}
