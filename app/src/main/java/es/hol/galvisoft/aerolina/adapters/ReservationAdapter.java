package es.hol.galvisoft.aerolina.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.List;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.utils.TimeUtil;

/**
 * Creado por Juan Carlos el dia 31/05/2015.
 */
public class ReservationAdapter extends ArrayAdapter<ParseObject> {

    public ReservationAdapter(Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.item_reservations, parent, false);
        }
        ParseObject reservation = getItem(position);

        ((TextView) convertView.findViewById(R.id.code)).setText(reservation.getObjectId());
        ParseObject originAirport = reservation.getParseObject("flight").getParseObject("origin");
        ParseObject destinationAirport = reservation.getParseObject("flight").getParseObject("destination");
        ((TextView) convertView.findViewById(R.id.flight)).setText(
                originAirport.getString("iata") + " ► " + destinationAirport.getString("iata"));
        String formatedDate = TimeUtil.millisToDateTime(reservation.getParseObject("flight").getDate("departureDate"));
        ((TextView) convertView.findViewById(R.id.date)).setText(formatedDate);
        if (reservation.getBoolean("checkin")) {
            ((TextView) convertView.findViewById(R.id.checkin)).setText("CheckIn realizado");
            ((TextView) convertView.findViewById(R.id.seat)).setText(reservation
                    .getParseObject("seat")
                    .getString("code"));
        } else {
            ((TextView) convertView.findViewById(R.id.checkin)).setText("CheckIn no realizado");
            ((TextView) convertView.findViewById(R.id.seat)).setText("-");
        }
        ((TextView) convertView.findViewById(R.id.classType)).setText(reservation
                .getParseObject("classType")
                .getString("name"));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
