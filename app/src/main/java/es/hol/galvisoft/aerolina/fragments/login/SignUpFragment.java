package es.hol.galvisoft.aerolina.fragments.login;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.activities.UserActivity;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private UserActivity userActivity;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    public SignUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_sign_up, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.signup).setOnClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userActivity = (UserActivity) activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup: {
                Spinner spDocumentType = (Spinner) getView().findViewById(R.id.document_type);
                TextView tvDocumentNumber = (TextView) getView().findViewById(R.id.document_number);
                TextView tvName = (TextView) getView().findViewById(R.id.name);
                TextView tvLastName = (TextView) getView().findViewById(R.id.lastname);
                TextView tvEmail = (TextView) getView().findViewById(R.id.email);
                TextView tvUserName = (TextView) getView().findViewById(R.id.username);
                TextView tvPassword = (TextView) getView().findViewById(R.id.password);
                TextView tvPasswordConfirm = (TextView) getView().findViewById(R.id.password_confirm);
                if (TextUtils.isEmpty(tvDocumentNumber.getText())) {
                    tvDocumentNumber.setError("Este campo es requerido");
                    tvDocumentNumber.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(tvName.getText())) {
                    tvName.setError("Este campo es requerido");
                    tvName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(tvLastName.getText())) {
                    tvLastName.setError("Este campo es requerido");
                    tvLastName.requestFocus();
                    return;
                }
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
                if (TextUtils.isEmpty(tvUserName.getText())) {
                    tvUserName.setError("Este campo es requerido");
                    tvUserName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(tvPassword.getText())) {
                    tvPassword.setError("Este campo es requerido");
                    tvPassword.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(tvPasswordConfirm.getText())) {
                    tvPasswordConfirm.setError("Este campo es requerido");
                    tvPasswordConfirm.requestFocus();
                    return;
                }
                if (!tvPassword.getText().toString().equalsIgnoreCase(tvPasswordConfirm.getText().toString())) {
                    tvPasswordConfirm.setError("Las contraseñas no coinciden");
                    tvPasswordConfirm.requestFocus();
                    return;
                }
                ParseUser user = new ParseUser();
                user.setUsername(tvUserName.getText().toString());
                user.setPassword(tvPassword.getText().toString());
                user.setEmail(tvEmail.getText().toString());

                user.put("documentType", spDocumentType.getSelectedItemPosition());
                user.put("documentNumber", tvDocumentNumber.getText().toString());
                user.put("name", tvName.getText().toString());
                user.put("lastName", tvLastName.getText().toString());

                final ProgressDialog progressDialog = ProgressDialog.show(userActivity, "Registrando nueva cuenta", "por favor espere...", true, false);
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            userActivity.setResult(Activity.RESULT_OK);
                            userActivity.finish();
                        } else {
                            Toast.makeText(userActivity, "Se ha producido un error "+e.getCode()+" "+e.toString(), Toast.LENGTH_LONG).show();
                        }
                        try {
                            progressDialog.dismiss();
                        } catch (Exception ignored) {
                        }
                    }
                });
                break;
            }
        }
    }
}
