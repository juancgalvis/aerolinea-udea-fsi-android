package es.hol.galvisoft.aerolina.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.hol.galvisoft.aerolina.fragments.CustomDialogFragment;
import es.hol.galvisoft.aerolina.model.Airport;

/**
 * Creado por Juan Carlos el dia 13/05/2015.
 */
public class AirportAdapter extends ArrayAdapter<Airport> implements AdapterView.OnItemClickListener {
    private CustomDialogFragment customDialogFragment;

    public AirportAdapter(CustomDialogFragment customDialogFragment, int resource, List<Airport> objects) {
        super(customDialogFragment.getActivity(), resource, objects);
        this.customDialogFragment = customDialogFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(getContext());
        }
        Airport a = getItem(position);
        ((TextView) convertView).setText(a.getIata() + "-" + a.getName() + ", " + a.getCity());
        convertView.setPadding(10, 10, 10, 10);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Airport a = getItem(position);
        customDialogFragment.onItemClick(a.getId(), ((TextView) view).getText().toString());
    }
}
