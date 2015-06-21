package es.hol.galvisoft.aerolina.fragments.find;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.activities.FindActivity;
import es.hol.galvisoft.aerolina.adapters.FlightAdapter;
import es.hol.galvisoft.aerolina.model.Flight;
import es.hol.galvisoft.aerolina.services.APIService;

/**
 * Creado por Juan Carlos el dia 31/05/2015.
 */
public class SelectRoundFlightFragment extends Fragment {
    private List<Flight> flights;
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
        return inflater.inflate(R.layout.fragment_find_select_round_flight, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureUI();
    }

    private void configureUI() {
        lvFlights = (ListView) context.findViewById(R.id.list_flights);
        tableTitles = (LinearLayout) context.findViewById(R.id.table_titles);
        new FindFligthTask().execute(Long.toString(context.getRoundAirport()), Long.toString(context.getTripAirport()), context.getRoundDate());
    }

    public boolean next() {
        return false;
    }

    private class FindFligthTask extends AsyncTask<String, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressDialog = ProgressDialog.show(context, null, "Consultando vuelos\nPor favor espere...", true, false);
            } catch (Exception ignored) {
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            flights = APIService.getFlights(params[0], params[1], params[2]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                progressDialog.dismiss();
            } catch (Exception ignored) {
            }
            if (flights.isEmpty()) {
                flights.add(new Flight(-1, null, null, 0, null, null));
                tableTitles.setVisibility(View.GONE);
            } else {
                tableTitles.setVisibility(View.VISIBLE);
            }
            lvFlights.setAdapter(new FlightAdapter(context, R.layout.item_flights, flights));
        }
    }

}
