package es.hol.galvisoft.aerolina.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.utils.TimeUtil;

/**
 * Creado por Juan Carlos el dia 31/05/2015.
 */
public class FlightAdapter extends ArrayAdapter<ParseObject> {
    private int classType;
    private double percentages[] = new double[]{0.6, 0.8, 1};

    public FlightAdapter(Context context, int resource, List<ParseObject> objects, int classType) {
        super(context, resource, objects);
        this.classType = classType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.item_flights, parent, false);
        }
        ParseObject flight = getItem(position);
        if (flight.getObjectId() == null) {
            ((TextView) convertView.findViewById(R.id.departure)).setText("No hay vuelos disponibles");
            convertView.findViewById(R.id.code).setVisibility(View.GONE);
            convertView.findViewById(R.id.arrive).setVisibility(View.GONE);
            convertView.findViewById(R.id.price).setVisibility(View.GONE);
        } else {
            ((TextView) convertView.findViewById(R.id.code)).setText(flight.getObjectId());
            ((TextView) convertView.findViewById(R.id.departure)).setText(TimeUtil.millisToTime(flight.getDate("departureDate").getTime()));
            ((TextView) convertView.findViewById(R.id.arrive)).setText(TimeUtil.millisToTime(flight.getDate("arrivalDate").getTime()));
            ((TextView) convertView.findViewById(R.id.price)).setText(Double.toString(flight.getDouble("price") * percentages[classType]));
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        if (getItem(position).getObjectId() == null) {
            return -1;
        }
        return position;
    }
}
