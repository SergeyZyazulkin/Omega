package pvms.omega.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import pvms.omega.R;
import pvms.omega.model.BackgroundMusic;

public class SettingsScreen extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        this.getListView().setBackgroundDrawable(
                this.getResources().getDrawable(R.drawable.background));
        this.getWindow().setBackgroundDrawable(
                this.getResources().getDrawable(R.drawable.background));
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
