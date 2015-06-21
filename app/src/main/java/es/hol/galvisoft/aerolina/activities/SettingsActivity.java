package es.hol.galvisoft.aerolina.activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.hol.galvisoft.aerolina.R;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        configureHeader();
        getFragmentManager().beginTransaction().replace(R.id.layout, new CustomPreferenceFragment()).commit();
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

    public void close(View view) {
        finish();
    }

    public static class CustomPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        private static final String DOC_TYPE = "doc_type", DOC_NUM = "doc_num", FIRST_LASTNAME = "first_lastname";
        private Preference docType, docNum, firstLastname;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_user_data);
            docType = findPreference(DOC_TYPE);
            docNum = findPreference(DOC_NUM);
            firstLastname = findPreference(FIRST_LASTNAME);
            cargarPreferences();
            docType.setOnPreferenceChangeListener(this);
            docNum.setOnPreferenceChangeListener(this);
            firstLastname.setOnPreferenceChangeListener(this);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference.getKey().equalsIgnoreCase(DOC_TYPE)) {
                newValue = ((ListPreference) docType).getEntry();
            }
            preference.setSummary(newValue.toString());
            return true;
        }

        private void cargarPreferences() {
            docType.setSummary(((ListPreference) docType).getEntry());
            docNum.setSummary(((EditTextPreference) docNum).getText());
            firstLastname.setSummary(((EditTextPreference) firstLastname).getText());
        }
    }
}
