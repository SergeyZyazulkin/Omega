package pvms.omega.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Game {

    private static final int FIELD_SIZE = 10;
    private static final int RIGHT = 0;
    private static final int BOTTOM = 1;
    private static final int LEFT = 2;
    private static final int TOP = 3;

    public enum Difficulty {
        EASY,
        NORMAL,
        HARD
    }

    private GameField gameField;
    private ArrayList<Creature> enemies;
    private ArrayList<Coordinates> dangerCells;
    private ArrayList<Coordinates> freeCells;
    private Creature player;
    private Random random;
    private int blocksCount;
    private int turn;
    private boolean ended;

    public Game(Difficulty difficulty) {
        this.gameField = new GameField(FIELD_SIZE);
        this.enemies = new ArrayList<>();
        HashSet<Coordinates> coordinates = new HashSet<>();
        this.random = new Random();
        Coordinates playerCoordinates = generateCoords();
        coordinates.add(playerCoordinates);
        this.player = new Creature(1, playerCoordinates);
        int enemiesCount = 0;
        int enemySpeed = 1;

        switch (difficulty) {
            case EASY:
                enemiesCount = 0;
                this.blocksCount = 1;
                break;

            case NORMAL:
                enemiesCount = 5;
                this.blocksCount = 1;
                this.player.setSpeed(2);
                break;

            case HARD:
                enemiesCount = 10;
                this.blocksCount = 2;
                enemySpeed = 2;
                this.player.setSpeed(2);
        }

        for (int i = 0; i < enemiesCount; ++i) {
            Coordinates enemyCoords;

            do {
                enemyCoords = generateCoords();
            } while (coordinates.contains(enemyCoords));


            coordinates.add(enemyCoords);
            Creature enemy = new Creature(enemySpeed, enemyCoords);
            enemy.setDirection(random.nextInt(4));
            enemies.add(enemy);
        }

        this.turn = 1;
        this.dangerCells = new ArrayList<>();
        this.freeCells = new ArrayList<>();
        this.ended = false;

        for (int i = 0; i < FIELD_SIZE; ++i) {
            for (int j = 0; j < FIELD_SIZE; ++j) {
                this.freeCells.add(new Coordinates(i, j));
            }
        }
    }

    public boolean canMove(Coordinates coordinates) {
        return !(coordinates.getX() >= FIELD_SIZE || coordinates.getX() < 0 ||
                coordinates.getY() >= FIELD_SIZE || coordinates.getY() < 0) && !ended &&
                Math.abs(coordinates.getX() - player.getCoordinates().getX()) +
                        Math.abs(coordinates.getY() - player.getCoordinates().getY())
                        <= player.getSpeed() && gameField.getState(coordinates) !=
                GameField.CellState.CLOSED;

    }

    public boolean move(Coordinates coordinates) {
        player.setCoordinates(coordinates);

        for (Coordinates dangerCoords : dangerCells) {
            gameField.setState(GameField.CellState.CLOSED, dangerCoords);

            if (dangerCoords.equals(coordinates)) {
                ended = true;
                return false;
            }
        }

        dangerCells.clear();

        for (int i = 0; i < blocksCount; ++i) {
            if (!freeCells.isEmpty()) {
                int index = random.nextInt(freeCells.size());
                Coordinates dangerCoords = freeCells.get(index);
                gameField.setState(GameField.CellState.DANGER, dangerCoords);
                dangerCells.add(dangerCoords);
                freeCells.remove(index);
            }
        }

        for (Creature enemy : enemies) {
            if (enemy.getCoordinates().equals(coordinates)) {
                ended = true;
                return false;
            }

            if (move(enemy)) {
                ended = true;
                return false;
            }
        }

        ++turn;
        return true;
    }

    private Coordinates generateCoords() {
        int x = random.nextInt(FIELD_SIZE);
        int y = random.nextInt(FIELD_SIZE);
        return new Coordinates(x, y);
    }

    private boolean move(Creature enemy) {
        int steps = enemy.getSpeed();

        while (steps > 0) {
            int x = enemy.getCoordinates().getX();
            int y = enemy.getCoordinates().getY();

            switch (enemy.getDirection()) {
                case RIGHT:
                    if (x < FIELD_SIZE - 1 && gameField.getState(new Coordinates(x + 1, y)) ==
                            GameField.CellState.FREE) {
                        ++x;
                    } else {
                        enemy.setDirection(random.nextInt(4));
                        steps = 0;
                        break;
                    }

                    break;

                case BOTTOM:
                    if (y < FIELD_SIZE - 1 && gameField.getState(new Coordinates(x, y + 1)) ==
                            GameField.CellState.FREE) {
                        ++y;
                    } else {
                        enemy.setDirection(random.nextInt(4));
                        steps = 0;
                        continue;
                    }

                    break;

                case LEFT:
                    if (x > 0 && gameField.getState(new Coordinates(x - 1, y)) ==
                            GameField.CellState.FREE) {
                        --x;
                    } else {
                        enemy.setDirection(random.nextInt(4));
                        steps = 0;
                        continue;
                    }

                    break;

                case TOP:
                    if (y > 0 && gameField.getState(new Coordinates(x, y - 1)) ==
                            GameField.CellState.FREE) {
                        --y;
                    } else {
                        enemy.setDirection(random.nextInt(4));
                        steps = 0;
                        continue;
                    }
            }

            enemy.setCoordinates(new Coordinates(x, y));
            --steps;

            if (enemy.getCoordinates().equals(player.getCoordinates())) {
                return true;
            }
        }

        return false;
        /*step = random.nextInt(step + 1);

        while (step > 0) {
            int direction = random.nextInt(4);
            int x = coordinates.getX();
            int y = coordinates.getY();

            switch (direction) {
                case RIGHT:
                    if (x < FIELD_SIZE - 1) {
                        ++x;
                    } else {
                        continue;
                    }

                    break;

                case BOTTOM:
                    if (y < FIELD_SIZE - 1) {
                        ++y;
                    } else {
                        continue;
                    }

                    break;

                case LEFT:
                    if (x > 0) {
                        --x;
                    } else {
                        continue;
                    }

                    break;

                case TOP:
                    if (y < 0) {
                        --y;
                    } else {
                        continue;
                    }
            }

            coordinates.setX(x);
            coordinates.setY(y);
            --step;
        }*/
    }

    public int getTurn() {
        return turn;
    }

    public GameField getGameField() {
        return gameField;
    }

    public Creature getPlayer() {
        return player;
    }

    public ArrayList<Creature> getEnemies() {
        return enemies;
    }

    public boolean isEnded() {
        return ended;
    }
}
