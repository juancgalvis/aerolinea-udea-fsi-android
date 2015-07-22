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

/**
 * Creado por Juan Carlos el dia 13/05/2015.
 */
public class SeatAdapter extends ArrayAdapter<ParseObject> {

    public SeatAdapter(Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.item_seat, parent, false);
        }
        ParseObject seat = getItem(position);
        ((TextView) convertView.findViewById(R.id.seat_code)).setText(seat.getString("code"));
        return convertView;
    }
}
