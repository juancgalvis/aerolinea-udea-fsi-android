package es.hol.galvisoft.aerolina.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.fragments.login.LoginFragment;
import es.hol.galvisoft.aerolina.fragments.login.LogoutFragment;
import es.hol.galvisoft.aerolina.fragments.login.LostPasswordFragment;
import es.hol.galvisoft.aerolina.fragments.login.SignUpFragment;

public class UserActivity extends Activity {
    private int currentFragment;
    public static final int LOGIN_FRAGMENT = 0;
    public static final int SIGN_UP_FRAGMENT = 1;
    public static final int LOST_PASSWORD_FRAGMENT = 2;
    public static final int LOGOUT_FRAGMENT = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        configurarUI();
    }

    private void configurarUI() {
        configureHeader();
        if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().isAuthenticated()) {
            setFragment(LOGOUT_FRAGMENT);
        } else {
            setFragment(LOGIN_FRAGMENT);
        }
    }


    private void configureHeader() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.title_activity_settings);
        TextView subtitle = (TextView) findViewById(R.id.subtitle);
        subtitle.setText(R.string.subtitle_activity_settings);
        ImageView icon = (ImageView) findViewById(R.id.subtitle_icon);
        icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_settings));
        findViewById(R.id.back).setVisibility(View.VISIBLE);
    }

    public void back(View view) {
        onBackPressed();
    }

    public void setFragment(int whichFragment) {
        currentFragment = whichFragment;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (whichFragment) {
            case LOGIN_FRAGMENT: {
                fragment = LoginFragment.newInstance();
                break;
            }
            case SIGN_UP_FRAGMENT: {
                fragment = SignUpFragment.newInstance();
                break;
            }
            case LOST_PASSWORD_FRAGMENT: {
                fragment = LostPasswordFragment.newInstance();
                break;
            }
            case LOGOUT_FRAGMENT: {
                fragment = LogoutFragment.newInstance();
                break;
            }
        }
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        switch (currentFragment) {
            case LOGIN_FRAGMENT: {
                setResult(RESULT_CANCELED);
                finish();
                break;
            }
            case SIGN_UP_FRAGMENT: {
                setFragment(LOGIN_FRAGMENT);
                break;
            }
            case LOST_PASSWORD_FRAGMENT: {
                setFragment(LOGIN_FRAGMENT);
                break;
            }
            case LOGOUT_FRAGMENT: {
                finish();
                break;
            }
        }
    }
}
