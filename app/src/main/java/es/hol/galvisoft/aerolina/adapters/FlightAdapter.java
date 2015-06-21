package es.hol.galvisoft.aerolina.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.model.Flight;

/**
 * Creado por Juan Carlos el dia 31/05/2015.
 */
public class FlightAdapter extends ArrayAdapter<Flight> implements AdapterView.OnItemClickListener {

    public FlightAdapter(Context context, int resource, List<Flight> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.item_flights, parent, false);
        }
        Flight flight = getItem(position);
        if (flight.getId() == -1) {
            ((TextView) convertView.findViewById(R.id.departure)).setText("No hay vuelos disponibles");
            convertView.findViewById(R.id.code).setVisibility(View.GONE);
            convertView.findViewById(R.id.arrive).setVisibility(View.GONE);
            convertView.findViewById(R.id.price).setVisibility(View.GONE);
        } else {
            ((TextView) convertView.findViewById(R.id.code)).setText("FSI-" + flight.getId());
            ((TextView) convertView.findViewById(R.id.departure)).setText(flight.getDeparture_time());
            ((TextView) convertView.findViewById(R.id.arrive)).setText(flight.getArrival_time());
            ((TextView) convertView.findViewById(R.id.price)).setText(Double.toString(flight.getPrice()));
        }
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Flight a = getItem(position);
    }
}
