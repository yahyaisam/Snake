// Koordinatene til eplene
public class Apple {
    public int x;
    public int y;

    public Apple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
