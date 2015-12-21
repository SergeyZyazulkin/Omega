package pvms.omega.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import pvms.omega.R;
import pvms.omega.model.BackgroundMusic;
import pvms.omega.model.Coordinates;
import pvms.omega.model.Creature;
import pvms.omega.model.Game;
import pvms.omega.model.Leaderboard;

public class GameCanvas extends View {

    private Coordinates angle;
    private int size;
    private Game game;
    private int cellSize;
    private GameScreen parent;
    private Boolean anim;
    private boolean end;
    private Game.Difficulty difficulty;
    private boolean animationEnabled;
    private boolean musicEnabled;

    public GameCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        anim = false;

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String strDifficulty = settings.getString("pref_difficulty", "Normal");

        if (strDifficulty == null) {
            strDifficulty = "Normal";
        }

        if (strDifficulty.equals("Easy")) {
            difficulty = Game.Difficulty.EASY;
        } else if (strDifficulty.equals("Normal")) {
            difficulty = Game.Difficulty.NORMAL;
        } else {
            difficulty = Game.Difficulty.HARD;
        }

        animationEnabled = settings.getBoolean("pref_enable_animation", true);
        musicEnabled = settings.getBoolean("pref_enable_music", true);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = ((int) motionEvent.getX() - angle.getX()) / cellSize;
                int y = ((int) motionEvent.getY() - angle.getY()) / cellSize;
                final Coordinates touchCoordinates = new Coordinates(x, y);

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (animationEnabled) {
                        if (anim) {
                            return true;
                        }

                        if (game.canMove(touchCoordinates)) {
                            final Animation fadeIn = AnimationUtils.loadAnimation(
                                    parent, R.anim.fade_in);
                            Animation fadeOut = AnimationUtils.loadAnimation(
                                    parent, R.anim.fade_out);
                            fadeIn.setFillAfter(true);
                            fadeOut.setFillAfter(true);

                            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {}

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    performMove(touchCoordinates);
                                    GameCanvas.this.startAnimation(fadeIn);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {}
                            });

                            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {}

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    checkGameState();
                                    anim = false;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {}
                            });

                            anim = true;
                            startAnimation(fadeOut);
                        }

                    } else {
                        if (game.canMove(touchCoordinates)) {
                            performMove(touchCoordinates);
                            checkGameState();
                        }
                    }
                }

                return true;
            }
        });

        game = new Game(difficulty);
    }

    public void setParent(GameScreen parent) {
        this.parent = parent;
    }

    private void setSize(int width, int height) {
        if (height > width) {
            size = width;
            angle = new Coordinates(0, (height - width) / 2);
        } else {
            size = height;
            angle = new Coordinates((width - height) / 2, 0);
        }

        cellSize = size / game.getGameField().getSize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setSize(canvas.getWidth(), canvas.getHeight());

        for (int i = 0; i < game.getGameField().getSize(); ++i) {
            for (int j = 0; j < game.getGameField().getSize(); ++j) {
                Paint paint = new Paint();

                switch (game.getGameField().getState(new Coordinates(i, j))) {
                    case FREE:
                        paint.setColor(Color.WHITE);
                        canvas.drawRect(angle.getX() + i * cellSize,
                                angle.getY() + j * cellSize,
                                angle.getX() + (i + 1) * cellSize,
                                angle.getY() + (j + 1) * cellSize,
                                paint);
                        break;

                    case DANGER:
                        paint.setColor(Color.GRAY);
                        canvas.drawRect(angle.getX() + i * cellSize,
                                angle.getY() + j * cellSize,
                                angle.getX() + (i + 1) * cellSize,
                                angle.getY() + (j + 1) * cellSize,
                                paint);
                        break;

                    case CLOSED:
                        paint.setColor(Color.WHITE);
                        canvas.drawRect(angle.getX() + i * cellSize,
                                angle.getY() + j * cellSize,
                                angle.getX() + (i + 1) * cellSize,
                                angle.getY() + (j + 1) * cellSize,
                                paint);
                        getDrawable(R.drawable.block, new Coordinates(i, j)).draw(canvas);
                }
            }
        }

        for (int i = 0; i <= game.getGameField().getSize(); ++i) {
            Paint paint = new Paint();
            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            canvas.drawLine(angle.getX(), angle.getY() + cellSize * i, angle.getX() + size,
                    angle.getY() + cellSize * i, paint);
            canvas.drawLine(angle.getX() + i * cellSize, angle.getY(), angle.getX() + i * cellSize,
                    angle.getY() + size, paint);
        }

        getDrawable(R.drawable.android, game.getPlayer().getCoordinates()).draw(canvas);

        for (Creature enemy : game.getEnemies()) {
            getDrawable(R.drawable.enemy, enemy.getCoordinates()).draw(canvas);
        }
    }

    private void performMove(Coordinates touchCoordinates) {
        end = !game.move(touchCoordinates);
        GameCanvas.this.invalidate();
        Toast.makeText(parent, String.format(parent.getString(
                        R.string.game_screen_turn), game.getTurn()),
                Toast.LENGTH_SHORT).show();
    }

    private void checkGameState() {
        if (end) {
            if (musicEnabled) {
                parent.stopService(new Intent(parent, BackgroundMusic.class));
            }

            Leaderboard leaderboard = Leaderboard.load(parent);
            String name = parent.getIntent().
                    getExtras().getString("name");

            switch (difficulty) {
                case EASY:
                    leaderboard.addEasyRecord(name, game.getTurn());
                    break;

                case NORMAL:
                    leaderboard.addNormalRecord(name, game.getTurn());
                    break;

                case HARD:
                    leaderboard.addHardRecord(name, game.getTurn());
            }

            leaderboard.save(parent);

            new AlertDialog.Builder(parent)
                    .setMessage(parent.getString(
                            R.string.game_screen_game_over))
                    .setPositiveButton(parent.getString(R.string.
                                    game_screen_button_return),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    SharedPreferences settings = PreferenceManager.
                                            getDefaultSharedPreferences(parent);
                                    boolean soundEnabled = settings.getBoolean(
                                            "pref_enable_sound", true);

                                    if (soundEnabled) {
                                        MediaPlayer player = MediaPlayer.create(parent, R.raw.press);
                                        player.setLooping(false);
                                        player.start();
                                    }

                                    parent.finish();
                                    parent.overridePendingTransition(
                                            R.anim.slide_in_left,
                                            R.anim.slide_out_right);
                                }
                            })
                    .setNegativeButton(parent.getString(R.string.
                                    game_screen_button_restart),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    SharedPreferences settings = PreferenceManager.
                                            getDefaultSharedPreferences(parent);
                                    boolean soundEnabled = settings.getBoolean("pref_enable_sound", true);

                                    if (soundEnabled) {
                                        MediaPlayer player = MediaPlayer.create(parent, R.raw.press);
                                        player.setLooping(false);
                                        player.start();
                                    }

                                    game = new Game(difficulty);

                                    if (musicEnabled) {
                                        parent.startService(new Intent(parent, BackgroundMusic.class));
                                    }

                                    invalidate();
                                }
                            })
                    .show();
        }
    }

    public Drawable getDrawable(int id, Coordinates coordinates) {
        Drawable picture = getResources().getDrawable(id);
        Bitmap bitmap = ((BitmapDrawable) picture).getBitmap();
        Drawable drawable = new BitmapDrawable(getResources(),
                Bitmap.createScaledBitmap(bitmap, cellSize, cellSize, true));
        drawable.setBounds(angle.getX() + coordinates.getX() * cellSize,
                angle.getY() + coordinates.getY() * cellSize,
                angle.getX() + (coordinates.getX() + 1) * cellSize,
                angle.getY() + (coordinates.getY() + 1) * cellSize);
        return drawable;
    }

    public boolean playMusic() {
        return !game.isEnded();
    }
}
