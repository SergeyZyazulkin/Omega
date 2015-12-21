package pvms.omega.model;

public class GameField {

    public enum CellState {
        FREE,
        DANGER,
        CLOSED
    }

    private int size;
    private CellState[][] field;

    public GameField(int size) {
        this.size = size;
        this.field = new CellState[size][size];
        reset();
    }

    public void reset() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                field[i][j] = CellState.FREE;
            }
        }
    }

    public void setState(CellState state, Coordinates coordinates) {
        field[coordinates.getX()][coordinates.getY()] = state;
    }

    public CellState getState(Coordinates coordinates) {
        return field[coordinates.getX()][coordinates.getY()];
    }

    public int getSize() {
        return size;
    }
}
