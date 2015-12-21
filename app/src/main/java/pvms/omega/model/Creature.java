package pvms.omega.model;

public class Creature {

    private int speed;
    private Coordinates coordinates;
    private int direction;

    public Creature(int speed, Coordinates coordinates) {
        this.speed = speed;
        this.coordinates = coordinates;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
