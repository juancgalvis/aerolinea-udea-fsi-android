package es.hol.galvisoft.aerolina.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.adapters.OptionsMainAdapter;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.option_list);
        listView.setAdapter(new OptionsMainAdapter(this));
        listView.setOnItemClickListener((OptionsMainAdapter) listView.getAdapter());
    }

}
