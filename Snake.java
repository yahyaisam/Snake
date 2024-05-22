// Koordinatene til slangen
public class Snake {
    public int x;
    public int y;

    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
