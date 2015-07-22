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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.activities.UserActivity;

public class LostPasswordFragment extends Fragment {

    private UserActivity userActivity;

    public static LostPasswordFragment newInstance() {
        return new LostPasswordFragment();
    }

    public LostPasswordFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_lost_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.remember_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvEmail = (TextView) getView().findViewById(R.id.email);
                if (TextUtils.isEmpty(tvEmail.getText())) {
                    tvEmail.setError("Este campo es requerido");
                    tvEmail.requestFocus();
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(tvEmail.getText()).matches()) {
                    tvEmail.setError("El correo ingresado no es valido");
                    tvEmail.requestFocus();
                    return;
                }
                final ProgressDialog progressDialog = ProgressDialog.show(userActivity, "Enviando correo de recuperación", "por favor espere...", true, false);
                ParseUser.requestPasswordResetInBackground(tvEmail.getText().toString(), new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        try {
                            progressDialog.dismiss();
                        } catch (Exception ignored) {
                        }
                        if (e == null) {
                            Toast.makeText(userActivity, "Revisa tu correo y sigue las instrucciones", Toast.LENGTH_LONG).show();
                            userActivity.setFragment(UserActivity.LOGIN_FRAGMENT);
                        } else {
                            Toast.makeText(userActivity, "La dirección de correo suministrada no se encuentra registrada", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userActivity = (UserActivity) activity;
    }

}
