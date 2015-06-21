package es.hol.galvisoft.aerolina.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.fragments.find.MainFragment;
import es.hol.galvisoft.aerolina.fragments.find.SelectRoundFlightFragment;

public class FindActivity extends Activity {
    public static final int MAIN_FRAGMENT = 0;
    public static final int SELECT_ROUND_FLIGHT_FRAGMENT = 1;

    private MainFragment mainFragment;
    private SelectRoundFlightFragment selectRoundFlightFragment;

    private TextView subtitle;

    private String roundDate, tripDate;
    private int currentFragment;
    private long roundAirport, tripAirport;
    private boolean isRoundTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        configureUI();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            setFragment(currentFragment - 1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void configureUI() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.title_activity_find);
        subtitle = (TextView) findViewById(R.id.subtitle);
        ImageView icon = (ImageView) findViewById(R.id.subtitle_icon);
        icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_find));
        findViewById(R.id.back).setVisibility(View.VISIBLE);
        setFragment(MAIN_FRAGMENT);
    }

    public void close(View view) {
        finish();
    }

    public void back(View view) {
        setFragment(currentFragment - 1);
    }

    public void next(View view) {
        switch (currentFragment) {
            case MAIN_FRAGMENT: {
                if (mainFragment.next()) {
                    roundAirport = mainFragment.getOriginCity();
                    tripAirport = mainFragment.getDestinyCity();
                    isRoundTrip = mainFragment.isRoundTrip();
                    roundDate = mainFragment.getDateRound();
                    tripDate = mainFragment.getDateTrip();
                    setFragment(currentFragment + 1);
                }
                break;
            }
            case SELECT_ROUND_FLIGHT_FRAGMENT: {
                if (selectRoundFlightFragment.next()) {
                    /*roundAirport = mainFragment.getOriginCity();
                    tripAirport = mainFragment.getDestinyCity();
                    isRoundTrip = mainFragment.isRoundTrip();
                    roundDate = mainFragment.getDateRound();
                    tripDate = mainFragment.getDateTrip();*/
                }
                break;
            }
        }
    }

    public void setFragment(int posicion) {
        if (posicion < MAIN_FRAGMENT) {
            finish();
        }
        currentFragment = posicion;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (posicion) {
            case MAIN_FRAGMENT: {
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                }
                ft.replace(R.id.fragment, mainFragment);
                subtitle.setText(R.string.subtitle_activity_find);
                break;
            }
            case SELECT_ROUND_FLIGHT_FRAGMENT: {
                if (selectRoundFlightFragment == null) {
                    selectRoundFlightFragment = new SelectRoundFlightFragment();
                }
                ft.replace(R.id.fragment, selectRoundFlightFragment);
                subtitle.setText(R.string.subtitle_select_round_flight_find);
                break;
            }

        }
        ft.commit();
    }

    public void setOriginCity(long code, String name) {
        mainFragment.setOriginCity(code, name);
    }

    public void setDestinyCity(long code, String name) {
        mainFragment.setDestinyCity(code, name);
    }

    public void setDateRound(int year, int monthOfYear, int dayOfMonth) {
        mainFragment.setDateRound(year, monthOfYear, dayOfMonth);
    }

    public void setDateTrip(int year, int monthOfYear, int dayOfMonth) {
        mainFragment.setDateTrip(year, monthOfYear, dayOfMonth);
    }

    public String getRoundDate() {
        return roundDate;
    }

    public long getRoundAirport() {
        return roundAirport;
    }

    public String getTripDate() {
        return tripDate;
    }

    public long getTripAirport() {
        return tripAirport;
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }
}
