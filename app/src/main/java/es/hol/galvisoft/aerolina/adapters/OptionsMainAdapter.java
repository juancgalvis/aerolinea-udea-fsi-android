package es.hol.galvisoft.aerolina.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.activities.BoardingPassActivity;
import es.hol.galvisoft.aerolina.activities.FindActivity;
import es.hol.galvisoft.aerolina.activities.MyFlightsActivity;
import es.hol.galvisoft.aerolina.activities.StatisticsActivity;
import es.hol.galvisoft.aerolina.activities.UserActivity;

/**
 * Creado por Juan Carlos el dia 13/05/2015.
 */
public class OptionsMainAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private String options[];
    private int icons[] = new int[]{
            R.mipmap.ic_find_main,
            R.mipmap.ic_my_flights_main,
            R.mipmap.ic_pases_main,
            R.mipmap.ic_statistics_main,
            R.mipmap.ic_settings_main};
    private Context context;

    public OptionsMainAdapter(Activity activity) {
        context = activity;
        options = context.getResources().getStringArray(R.array.options_main_activity);
    }

    @Override
    public int getCount() {
        return options.length;
    }

    @Override
    public Object getItem(int position) {
        return options[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_option_main, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.title)).setText(options[position]);
        ((ImageView) convertView.findViewById(R.id.title_icon)).setImageResource(icons[position]);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position) {
            case 0: {
                intent = new Intent(context, FindActivity.class);
                break;
            }
            case 1: {
                intent = new Intent(context, MyFlightsActivity.class);
                break;
            }
            case 2: {
                intent = new Intent(context, BoardingPassActivity.class);
                break;
            }
            case 3: {
                intent = new Intent(context, StatisticsActivity.class);
                break;
            }
            case 4: {
                intent = new Intent(context, UserActivity.class);
                break;
            }
            default: {
                return;
            }
        }
        context.startActivity(intent);
    }
}
