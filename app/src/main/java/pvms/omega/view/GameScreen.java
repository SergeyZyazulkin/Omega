package pvms.omega.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import pvms.omega.R;
import pvms.omega.model.BackgroundMusic;
import pvms.omega.model.Game;


public class GameScreen extends Activity {
    
    private GameCanvas gcGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        gcGame = (GameCanvas) findViewById(R.id.gcGame);
        gcGame.setParent(this);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean musicEnabled = settings.getBoolean("pref_enable_music", true);

        if (musicEnabled && gcGame.playMusic()) {
            startService(new Intent(this, BackgroundMusic.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean musicEnabled = settings.getBoolean("pref_enable_music", true);

        if (musicEnabled && gcGame.playMusic()) {
            stopService(new Intent(this, BackgroundMusic.class));
        }
    }
}
