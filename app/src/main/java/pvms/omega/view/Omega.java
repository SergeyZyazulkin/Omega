package pvms.omega.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pvms.omega.R;
import pvms.omega.model.BackgroundMusic;


public class Omega extends Activity implements Animation.AnimationListener {

    private TextView tvO;
    private TextView tvM;
    private TextView tvE;
    private TextView tvG;
    private TextView tvA;

    private Button btPlay;
    private Button btTutorial;
    private Button btSettings;
    private Button btLeaderboards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        tvO = (TextView) findViewById(R.id.tvO);
        tvM = (TextView) findViewById(R.id.tvM);
        tvE = (TextView) findViewById(R.id.tvE);
        tvG = (TextView) findViewById(R.id.tvG);
        tvA = (TextView) findViewById(R.id.tvA);

        btPlay = (Button) findViewById(R.id.btPlay);
        btTutorial = (Button) findViewById(R.id.btTutorial);
        btSettings = (Button) findViewById(R.id.btOptions);
        btLeaderboards = (Button) findViewById(R.id.btLeaderboards);

        restartAnimation();

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = PreferenceManager.
                        getDefaultSharedPreferences(Omega.this);
                boolean soundEnabled = settings.getBoolean("pref_enable_sound", true);

                if (soundEnabled) {
                    MediaPlayer player = MediaPlayer.create(Omega.this, R.raw.press);
                    player.setLooping(false);
                    player.start();
                }

                final EditText etName = new EditText(Omega.this);
                etName.setGravity(Gravity.CENTER);
                etName.setHint(getString(R.string.dialog_edit_text_hint));

                new AlertDialog.Builder(Omega.this)
                        .setMessage(getString(R.string.dialog_text))
                        .setView(etName)
                        .setPositiveButton(getString(R.string.dialog_button_start),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        SharedPreferences settings = PreferenceManager.
                                                getDefaultSharedPreferences(Omega.this);
                                        boolean soundEnabled = settings.getBoolean(
                                                "pref_enable_sound", true);

                                        if (soundEnabled) {
                                            MediaPlayer player = MediaPlayer.create(
                                                    Omega.this, R.raw.press);
                                            player.setLooping(false);
                                            player.start();
                                        }

                                        String name = etName.getText().toString().trim();

                                        if (!name.isEmpty()) {
                                            Intent intent = new Intent(
                                                    Omega.this, GameScreen.class);
                                            intent.putExtra("name", name);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_in_right,
                                                    R.anim.slide_out_left);
                                        } else {
                                            Toast.makeText(Omega.this,
                                                    getString(R.string.dialog_error_name),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                        .setNegativeButton(getString(R.string.dialog_button_cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        SharedPreferences settings = PreferenceManager.
                                                getDefaultSharedPreferences(Omega.this);
                                        boolean soundEnabled = settings.getBoolean(
                                                "pref_enable_sound", true);

                                        if (soundEnabled) {
                                            MediaPlayer player = MediaPlayer.create(
                                                    Omega.this, R.raw.press);
                                            player.setLooping(false);
                                            player.start();
                                        }
                                    }
                                })
                        .show();
            }
        });

        btTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = PreferenceManager.
                        getDefaultSharedPreferences(Omega.this);
                boolean soundEnabled = settings.getBoolean("pref_enable_sound", true);

                if (soundEnabled) {
                    MediaPlayer player = MediaPlayer.create(Omega.this, R.raw.press);
                    player.setLooping(false);
                    player.start();
                }

                Intent intent = new Intent(Omega.this, TutorialScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = PreferenceManager.
                        getDefaultSharedPreferences(Omega.this);
                boolean soundEnabled = settings.getBoolean("pref_enable_sound", true);

                if (soundEnabled) {
                    MediaPlayer player = MediaPlayer.create(Omega.this, R.raw.press);
                    player.setLooping(false);
                    player.start();
                }

                Intent intent = new Intent(Omega.this, SettingsScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btLeaderboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = PreferenceManager.
                        getDefaultSharedPreferences(Omega.this);
                boolean soundEnabled = settings.getBoolean("pref_enable_sound", true);

                if (soundEnabled) {
                    MediaPlayer player = MediaPlayer.create(Omega.this, R.raw.press);
                    player.setLooping(false);
                    player.start();
                }

                Intent intent = new Intent(Omega.this, LeaderboardsScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        settings.registerOnSharedPreferenceChangeListener(
                new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                boolean soundEnabled = sharedPreferences.getBoolean("pref_enable_sound", true);

                if (soundEnabled) {
                    MediaPlayer player = MediaPlayer.create(Omega.this, R.raw.press);
                    player.setLooping(false);
                    player.start();
                }
            }
        });
    }

    private void restartAnimation() {
        Animation letterO = AnimationUtils.loadAnimation(this, R.anim.title_letter_o);
        letterO.setAnimationListener(this);
        tvO.clearAnimation();
        tvO.startAnimation(letterO);

        Animation letterM = AnimationUtils.loadAnimation(this, R.anim.title_letter_m);
        letterM.setAnimationListener(this);
        tvM.clearAnimation();
        tvM.startAnimation(letterM);

        Animation letterE = AnimationUtils.loadAnimation(this, R.anim.title_letter_e);
        letterE.setAnimationListener(this);
        tvE.clearAnimation();
        tvE.startAnimation(letterE);

        Animation letterG = AnimationUtils.loadAnimation(this, R.anim.title_letter_g);
        letterG.setAnimationListener(this);
        tvG.clearAnimation();
        tvG.startAnimation(letterG);

        Animation letterA = AnimationUtils.loadAnimation(this, R.anim.title_letter_a);
        letterA.setAnimationListener(this);
        tvA.clearAnimation();
        tvA.startAnimation(letterA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        restartAnimation();
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        animation.reset();
        animation.setStartOffset(0);
        animation.start();
    }
}
