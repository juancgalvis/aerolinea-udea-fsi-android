package es.hol.galvisoft.aerolina.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.adapters.OptionsMainAdapter;
import es.hol.galvisoft.aerolina.data.AirportDataManager;
import es.hol.galvisoft.aerolina.model.Airport;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.option_list);
        listView.setAdapter(new OptionsMainAdapter(this));
        listView.setOnItemClickListener((OptionsMainAdapter) listView.getAdapter());

    }

    private void crearAeropuertos() {
        AirportDataManager airportDataManager = new AirportDataManager(getApplicationContext());

        for (final Airport airport : airportDataManager.getAll()) {
            final ParseObject airportParse = ParseObject.create("Airport");
            if (airport.getId() == null) {
                airportParse.put("name", airport.getName());
                airportParse.put("city", airport.getCity());
                airportParse.put("iata", airport.getIata());
                airportParse.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Log.e("air", "INSERT INTO airport VALUES(\""
                                + airportParse.getObjectId() + "\",\""
                                + airportParse.getString("name") + "\",\""
                                + airportParse.getString("city") + "\",\""
                                + airportParse.getString("iata") + "\");");
                    }
                });
            }
        }
    }

}
