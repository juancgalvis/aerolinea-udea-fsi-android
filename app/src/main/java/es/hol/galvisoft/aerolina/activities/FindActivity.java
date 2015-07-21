package es.hol.galvisoft.aerolina.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.Date;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.fragments.find.MainFragment;
import es.hol.galvisoft.aerolina.fragments.find.ReserveFragment;
import es.hol.galvisoft.aerolina.fragments.find.SelectRoundFlightFragment;
import es.hol.galvisoft.aerolina.fragments.find.SelectTripFlightFragment;

public class FindActivity extends Activity {
    public static final int MAIN_FRAGMENT = 0;
    public static final int SELECT_ROUND_FLIGHT_FRAGMENT = 1;
    public static final int SELECT_TRIP_FLIGHT_FRAGMENT = 2;
    public static final int RESERVE_FRAGMENT = 3;

    private MainFragment mainFragment;
    private SelectRoundFlightFragment selectRoundFlightFragment;
    private SelectTripFlightFragment selectTripFlightFragment;
    private ReserveFragment reserveFragment;
    private int classType;

    private TextView subtitle;

    private Date roundDate, tripDate;
    private int currentFragment;
    private String roundAirport, tripAirport;
    private ParseObject roundFlight, tripFlight;
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
            back(null);
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

    public void back(View view) {
        switch (currentFragment) {
            case MAIN_FRAGMENT: {
                finish();
                break;
            }
            case SELECT_ROUND_FLIGHT_FRAGMENT: {
                setFragment(MAIN_FRAGMENT);
                break;
            }
            case SELECT_TRIP_FLIGHT_FRAGMENT: {
                setFragment(SELECT_ROUND_FLIGHT_FRAGMENT);
                break;
            }
            case RESERVE_FRAGMENT: {
                if (isRoundTrip) {
                    setFragment(SELECT_TRIP_FLIGHT_FRAGMENT);
                } else {
                    setFragment(SELECT_ROUND_FLIGHT_FRAGMENT);
                }
                break;
            }
        }
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
                    classType = mainFragment.getClassType();
                    setFragment(SELECT_ROUND_FLIGHT_FRAGMENT);
                }
                break;
            }
            case SELECT_ROUND_FLIGHT_FRAGMENT: {
                if (selectRoundFlightFragment.next()) {
                    if (isRoundTrip) {
                        setFragment(SELECT_TRIP_FLIGHT_FRAGMENT);
                    } else {
                        setFragment(RESERVE_FRAGMENT);
                    }
                }
                break;
            }
            case SELECT_TRIP_FLIGHT_FRAGMENT: {
                if (selectTripFlightFragment.next()) {
                    setFragment(RESERVE_FRAGMENT);
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
            case SELECT_TRIP_FLIGHT_FRAGMENT: {
                if (selectTripFlightFragment == null) {
                    selectTripFlightFragment = new SelectTripFlightFragment();
                }
                ft.replace(R.id.fragment, selectTripFlightFragment);
                subtitle.setText(R.string.subtitle_select_trip_flight_find);
                break;
            }
            case RESERVE_FRAGMENT: {
                if (reserveFragment == null) {
                    reserveFragment = new ReserveFragment();
                }
                ft.replace(R.id.fragment, reserveFragment);
                subtitle.setText(R.string.subtitle_confirm_reservation_find);
                break;
            }
        }
        ft.commit();
    }

    public void setOriginCity(String code, String name) {
        mainFragment.setOriginCity(code, name);
    }

    public void setDestinyCity(String code, String name) {
        mainFragment.setDestinyCity(code, name);
    }

    public void setDateRound(int year, int monthOfYear, int dayOfMonth) {
        mainFragment.setDateRound(year, monthOfYear, dayOfMonth);
    }

    public void setDateTrip(int year, int monthOfYear, int dayOfMonth) {
        mainFragment.setDateTrip(year, monthOfYear, dayOfMonth);
    }

    public Date getRoundDate() {
        return roundDate;
    }

    public String getRoundAirport() {
        return roundAirport;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public String getTripAirport() {
        return tripAirport;
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public ParseObject getRoundFlight() {
        return roundFlight;
    }

    public void setRoundFlight(ParseObject roundFlight) {
        this.roundFlight = roundFlight;
    }

    public ParseObject getTripFlight() {
        return tripFlight;
    }

    public void setTripFlight(ParseObject tripFlight) {
        this.tripFlight = tripFlight;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }
}
