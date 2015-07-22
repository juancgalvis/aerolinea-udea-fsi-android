package es.hol.galvisoft.aerolina.fragments.login;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.activities.UserActivity;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private UserActivity userActivity;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.login).setOnClickListener(this);
        view.findViewById(R.id.signup).setOnClickListener(this);
        view.findViewById(R.id.lostpassword).setOnClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userActivity = (UserActivity) activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login: {
                TextView tvUser = (TextView) getView().findViewById(R.id.user);
                final TextView tvPassword = (TextView) getView().findViewById(R.id.password);
                if (TextUtils.isEmpty(tvUser.getText())) {
                    tvUser.setError("Este campo es requerido");
                    tvUser.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(tvPassword.getText())) {
                    tvPassword.setError("Este campo es requerido");
                    tvPassword.requestFocus();
                    return;
                }
                final ProgressDialog progressDialog = ProgressDialog.show(userActivity, "Iniciando Sesión", "por favor espere...", true, false);
                ParseUser.logInInBackground(tvUser.getText().toString(), tvPassword.getText().toString(), new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        try {
                            progressDialog.dismiss();
                        } catch (Exception ignored) {
                        }
                        if (e == null && user != null) {
                            userActivity.setResult(Activity.RESULT_OK);
                            userActivity.finish();
                        } else {
                            tvPassword.setError("Datos de acceso incorrectos");
                            tvPassword.requestFocus();
                            Toast.makeText(userActivity, "Se ha producido un error " + e.getCode() + " " + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            }
            case R.id.signup: {
                userActivity.setFragment(UserActivity.SIGN_UP_FRAGMENT);
                break;
            }
            case R.id.lostpassword: {
                userActivity.setFragment(UserActivity.LOST_PASSWORD_FRAGMENT);
                break;
            }
        }
    }
}
