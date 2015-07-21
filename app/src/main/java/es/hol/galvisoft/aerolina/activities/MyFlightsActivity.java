package es.hol.galvisoft.aerolina.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.fragments.myflights.MainFragment;
import es.hol.galvisoft.aerolina.fragments.myflights.SeatFragment;

public class MyFlightsActivity extends Activity {
    private int currentFragment;
    public static final int MAIN_FRAGMENT = 0;
    public static final int SELECT_SEAT_FRAGMENT = 1;

    private ParseObject reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            startActivityForResult(new Intent(getApplicationContext(), UserActivity.class), 123);
        } else {
            setContentView(R.layout.activity_my_flights);
            configureHeader();
            setFragment(MAIN_FRAGMENT);
        }
    }

    private void configureHeader() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.title_activity_my_flights);
    }

    public void setFragment(int which) {
        currentFragment = which;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (which) {
            case MAIN_FRAGMENT: {
                fragment = new MainFragment();
                TextView subtitle = (TextView) findViewById(R.id.subtitle);
                subtitle.setText(R.string.subtitle_activity_my_flights);
                ImageView icon = (ImageView) findViewById(R.id.subtitle_icon);
                icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_my_flights));
                findViewById(R.id.back).setVisibility(View.VISIBLE);
                break;
            }
            case SELECT_SEAT_FRAGMENT: {
                fragment = new SeatFragment();
                TextView subtitle = (TextView) findViewById(R.id.subtitle);
                subtitle.setText(R.string.subtitle_activity_my_flights_select_seat);
                ImageView icon = (ImageView) findViewById(R.id.subtitle_icon);
                icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_seat));
                findViewById(R.id.back).setVisibility(View.VISIBLE);
                break;
            }
        }
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }

    public ParseObject getReservation() {
        return reservation;
    }

    public void setReservation(ParseObject flight) {
        this.reservation = flight;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 123 || resultCode != RESULT_OK) {
            finish();
        } else {
            setContentView(R.layout.activity_my_flights);
            configureHeader();
            setFragment(MAIN_FRAGMENT);
        }
    }

    public void back(View view) {
        if (currentFragment == MAIN_FRAGMENT) {
            finish();
        } else {
            setFragment(MAIN_FRAGMENT);
        }
    }
}
