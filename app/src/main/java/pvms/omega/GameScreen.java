package pvms.omega;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class GameScreen extends Activity {
    
    private GameCanvas gcGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        gcGame = (GameCanvas) findViewById(R.id.gcGame);

        /*Toast toast = Toast.makeText(
                this, String.format(getString(R.string.game_screen_turn), 1), Toast.LENGTH_LONG);
        toast.show();*/
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
