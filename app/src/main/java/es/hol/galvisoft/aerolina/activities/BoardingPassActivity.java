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
import es.hol.galvisoft.aerolina.fragments.boardingpass.BoardingFragment;
import es.hol.galvisoft.aerolina.fragments.boardingpass.QRFragment;

public class BoardingPassActivity extends Activity {
    private int currentFragment;
    public static final int MAIN_FRAGMENT = 0;
    public static final int BOARDING_PASS = 1;
    private ParseObject reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            startActivityForResult(new Intent(getApplicationContext(), UserActivity.class), 123);
        } else {
            setContentView(R.layout.activity_boarding_pass);
            configureUI();
            setFragment(MAIN_FRAGMENT);
        }
    }

    private void configureUI() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.title_activity_my_boarding_pass);
        TextView subtitle = (TextView) findViewById(R.id.subtitle);
        subtitle.setText(R.string.subtitle_activity_my_boarding_pass);
        ImageView icon = (ImageView) findViewById(R.id.subtitle_icon);
        icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_my_flights));
        findViewById(R.id.back).setVisibility(View.VISIBLE);
    }

    public void setFragment(int which) {
        currentFragment = which;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = null;
        switch (which) {
            case MAIN_FRAGMENT: {
                fragment = new QRFragment();
                break;
            }
            case BOARDING_PASS: {
                fragment = new BoardingFragment();
                break;
            }
        }
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 123 || resultCode != RESULT_OK) {
            finish();
        } else {
            setContentView(R.layout.activity_boarding_pass);
            configureUI();
            setFragment(MAIN_FRAGMENT);
        }
    }

    public ParseObject getReservation() {
        return reservation;
    }

    public void setReservation(ParseObject reservation) {
        this.reservation = reservation;
    }

    public void back(View v) {
        if (currentFragment == MAIN_FRAGMENT) {
            finish();
        } else {
            setFragment(MAIN_FRAGMENT);
        }
    }
}
