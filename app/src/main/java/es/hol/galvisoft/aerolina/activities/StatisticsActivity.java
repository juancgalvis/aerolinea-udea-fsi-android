package es.hol.galvisoft.aerolina.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.hol.galvisoft.aerolina.R;

public class StatisticsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        configureHeader();
    }

    private void configureHeader(){
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.title_activity_statisctics);
        TextView subtitle = (TextView) findViewById(R.id.subtitle);
        subtitle.setText(R.string.subtitle_activity_statisctics);
        ImageView icon = (ImageView) findViewById(R.id.subtitle_icon);
        icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_statistics));
        findViewById(R.id.back).setVisibility(View.VISIBLE);
    }

    public void back(View view){
        finish();
    }
}
