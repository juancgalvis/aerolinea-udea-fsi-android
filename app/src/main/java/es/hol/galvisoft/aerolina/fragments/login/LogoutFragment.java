package es.hol.galvisoft.aerolina.fragments.login;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.activities.UserActivity;

public class LogoutFragment extends Fragment implements View.OnClickListener {

    private UserActivity userActivity;

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    public LogoutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_logout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ParseUser user = ParseUser.getCurrentUser();
        view.findViewById(R.id.logout).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.username)).setText(user.getUsername());
        ((TextView) view.findViewById(R.id.name)).setText(user.get("name") + " " + user.get("lastName"));
        String documentType = getResources().getStringArray(R.array.documents_types)[(Integer) user.get("documentType")];
        ((TextView) view.findViewById(R.id.document)).setText(documentType + ": " + user.get("documentNumber"));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userActivity = (UserActivity) activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout: {
                ParseUser.logOut();
                userActivity.setFragment(UserActivity.LOGIN_FRAGMENT);
                break;
            }
        }
    }
}
