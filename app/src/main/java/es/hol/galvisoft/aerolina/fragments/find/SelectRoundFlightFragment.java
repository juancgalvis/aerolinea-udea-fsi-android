package es.hol.galvisoft.aerolina.fragments.find;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.List;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.activities.FindActivity;
import es.hol.galvisoft.aerolina.adapters.FlightAdapter;

/**
 * Creado por Juan Carlos el dia 31/05/2015.
 */
public class SelectRoundFlightFragment extends Fragment implements AdapterView.OnItemClickListener {
    private FindActivity context;
    private ListView lvFlights;
    private LinearLayout tableTitles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (FindActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_select_flight, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureUI();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (id != -1) {
            ParseObject parseObject = (ParseObject) lvFlights.getAdapter().getItem(position);
            context.setRoundFlight(parseObject);
            context.next(null);
        }
    }

    private void configureUI() {
        lvFlights = (ListView) context.findViewById(R.id.list_flights);
        lvFlights.setOnItemClickListener(this);
        tableTitles = (LinearLayout) context.findViewById(R.id.table_titles);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Flight");
        ParseObject originAirport = ParseObject.createWithoutData("Airport", context.getRoundAirport());
        query.whereEqualTo("origin", originAirport);
        ParseObject destinationAirport = ParseObject.createWithoutData("Airport", context.getTripAirport());
        query.whereEqualTo("destination", destinationAirport);
        Date departureDate = context.getRoundDate();
        query.whereGreaterThanOrEqualTo("departureDate", departureDate);
        Date departureDateEnd = new Date(departureDate.getTime() + 24 * 60 * 60 * 1000);
        query.whereLessThanOrEqualTo("departureDate", departureDateEnd);

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Consultando vuelos", "Por favor espere...", true, false);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> flights, ParseException e) {
                try {
                    progressDialog.dismiss();
                } catch (Exception ignored) {
                }
                if (e == null) {
                    if (flights.isEmpty()) {
                        flights.add(new ParseObject("Fligth"));
                        tableTitles.setVisibility(View.GONE);
                    } else {
                        tableTitles.setVisibility(View.VISIBLE);
                    }
                    lvFlights.setAdapter(new FlightAdapter(context, R.layout.item_flights, flights, context.getClassType()));
                } else {
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean next() {
        return true;
    }

}
