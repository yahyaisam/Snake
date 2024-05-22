import java.util.ArrayList;

public class SnakeModel {
    public SnakeGUI gui;
    public int score;

    public ArrayList<Snake> snakeBody = new ArrayList<>();
    public ArrayList<Apple> apples = new ArrayList<>();

    public int width;
    public int height;

    public boolean playing = true;
    public String direction = "Right"; // Slangen starter med å bevege seg østover

    public SnakeModel(int width, int height) {
        this.width = width;
        this.height = height;

        // Her skal slangespillet settes i gang
        getSnakeBody().add(new Snake(5, 5));
        getSnakeBody().add(new Snake(5, 4));
        getSnakeBody().add(new Snake(5, 3));
        getSnakeBody().add(new Snake(5, 2));

        updateDirection(direction);
        updateMove();
    }

    // Oppdaterer slangen:
    public void updateMove() {
        // Up, x (rad) - 1
        if (direction.equals("Up")) {
            Snake head = getSnakeBody().get(0);
            Snake newHead = new Snake(head.x - 1, head.y);
            getSnakeBody().add(0, newHead);
            getSnakeBody().remove(getSnakeBody().size() - 1);
            // for (Snake s : getSnakeBody()) {
            //     System.out.println(s.toString());
            // }
            // System.out.println();
        }

        // Down, x (rad) + 1
        if (direction.equals("Down")) {
            Snake head = getSnakeBody().get(0);
            Snake newHead = new Snake(head.x + 1, head.y);
            getSnakeBody().add(0, newHead);
            getSnakeBody().remove(getSnakeBody().size() - 1);
        }

        // Left, y (kolonne) - 1
        if (direction.equals("Left")) {
            Snake head = getSnakeBody().get(0);
            Snake newHead = new Snake(head.x, head.y - 1);
            getSnakeBody().add(0, newHead);
            getSnakeBody().remove(getSnakeBody().size() - 1);
        }

        // Right, y (kolonne) + 1
        if (direction.equals("Right")) {
            Snake head = getSnakeBody().get(0);
            Snake newHead = new Snake(head.x, head.y + 1);
            getSnakeBody().add(0, newHead);
            getSnakeBody().remove(getSnakeBody().size() - 1);
        }

        // Dersom slangen crasher i veggen
        if (getSnakeBody().get(0).x > width - 1 || getSnakeBody().get(0).y > height - 1) {
            playing = false;
        }

        if (getSnakeBody().get(0).x < 0 || getSnakeBody().get(0).y < 0) {
            playing = false;
        }
    }

    // I Main: Hvert 2. sekund kaller på updateMove() i SnakeModel
    // I SnakeGUI: Når vi trykker på en av de navigeringsknappene, sett updateDirection til riktig retning (knapp Right til "Right" osv)
    public void updateDirection(String newDirection) {
        direction = newDirection;
    }

    public ArrayList<Snake> getSnakeBody() {
        return snakeBody;
    }

    public ArrayList<Apple> getApples() {
        return apples;
    }

    public void increaseScore() {
        score++;
    }
}
