import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SnakeGUI {
    public SnakeModel snakeModel;
    public int antallEpler = trekk(1, 10);
    public JLabel[][] ruter;
    public JLabel scoreText;

    public SnakeGUI(SnakeModel snakeModel) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
    
        catch (Exception e) {
            System.exit(1);
        }

        this.snakeModel = snakeModel;
        this.ruter = new JLabel[snakeModel.width][snakeModel.height];

        gui();
        updateGUI();
    }

    // Oppdaterer slangen i GUI'et
    // Kalles hvert 2. sekund fra Main
    public void updateGUI() {
        Snake snake = snakeModel.getSnakeBody().get(0);
        for (int i = 0; i < snakeModel.getApples().size(); i++) {
            if (snake.x == snakeModel.getApples().get(i).x && snake.y == snakeModel.getApples().get(i).y) {
                Snake tail = snakeModel.getSnakeBody().get(snakeModel.getSnakeBody().size() - 1);
                snakeModel.getSnakeBody().add(snakeModel.getSnakeBody().size() - 1, tail);
                snakeModel.increaseScore();
                scoreText.setText("Score: " + snakeModel.score);
                snakeModel.getApples().remove(i);
                newApple();
            }
        }

        // Looper gjennom alle rutene og fargelegger dem hvite for å "resette" rutenettet
        for (int i = 0; i < snakeModel.width; i++) {
            for (int j = 0; j < snakeModel.height; j++) {
                boolean ifApple = false;

                for (int k = 0; k < snakeModel.getApples().size(); k++) {
                    if (snakeModel.getApples().get(k).x == i && snakeModel.getApples().get(k).y == j) {
                        ruter[i][j].setBackground(Color.RED);
                        ifApple = true;

                        break;
                    }
                }
                if (!ifApple) {
                    ruter[i][j].setBackground(Color.WHITE);
                }
            }
        }
        
        // Fargelegger slangen grønt
        for (int i = 0; i < snakeModel.getSnakeBody().size(); i++) {
            Snake body = snakeModel.getSnakeBody().get(i);
            ruter[body.x][body.y].setBackground(Color.GREEN);
            
            if (snakeModel.getSnakeBody().get(0).x == body.x && snakeModel.getSnakeBody().get(0).y == body.y && i != 0) {
                snakeModel.playing = false;
            }
        }
    }

    public void newApple() {
        int x = trekk(0, snakeModel.width - 1);
        int y = trekk(0, snakeModel.height - 1);

        boolean canSpawn = true;
        for (int i = 0; i < snakeModel.getSnakeBody().size(); i++) {
            Snake snake = snakeModel.getSnakeBody().get(i);

            if (x == snake.x && y == snake.y) {
                canSpawn = false;
            }
        }

        for (int i = 0; i < snakeModel.getApples().size(); i++) {
            Apple apple = snakeModel.getApples().get(i);
            if (x == apple.x && y == apple.y) {
                canSpawn = false;
            }
        }

        if (canSpawn) {
            snakeModel.getApples().add(new Apple(x, y));
        }

        else {
            newApple();
        }
    }

    public void initApples() {
        // Fargelegg eplet/eplene rødt
        for (int i = 0; i < antallEpler; i++) {
            newApple();
        }
    }

    public int trekk(int a, int b) {
		return (int)(Math.random() * (b - a+1) + a);
	}

    public void moveNorth() {
        snakeModel.updateDirection("Up");
    }

    public void moveSouth() {
        snakeModel.updateDirection("Down");
    }

    public void moveWest() {
        snakeModel.updateDirection("Left");
    }

    public void moveEast() {
        snakeModel.updateDirection("Right");
    }

    public void gui() {
        // Størrelser
        int cellWidth = 50;
        int cellHeight = 50;
        int buttonSize = (cellWidth * 2) + 10; // quit & score

        JFrame vindu = new JFrame("The Snake Game");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel hovedPanel = new JPanel();
        hovedPanel.setBackground(Color.WHITE);
        hovedPanel.setLayout(new BorderLayout());
        //hovedPanel.setBackground(Color.CYAN);
        vindu.add(hovedPanel);

        // Panelet for knappene
        JPanel knappPanel = new JPanel();
        knappPanel.setBackground(Color.WHITE);
        knappPanel.setLayout(new BorderLayout());

        // Knapp opp
        JButton oppKnapp = new JButton("Up");
        oppKnapp.setPreferredSize(new Dimension(30, 40));
        knappPanel.add(oppKnapp, BorderLayout.NORTH);

        // Funksjon for ned-knappen
        class Up implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveNorth();
            }
        }
        oppKnapp.addActionListener(new Up());

        // Knapp ned
        JButton nedKnapp = new JButton("Down");
        nedKnapp.setPreferredSize(new Dimension(30, 40));
        knappPanel.add(nedKnapp, BorderLayout.SOUTH);

        // Funksjon for ned-knappen
        class Down implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSouth();
            }
        }
        nedKnapp.addActionListener(new Down());

        // Knapp venstre
        JButton venstreKnapp = new JButton("Left");
        venstreKnapp.setPreferredSize(new Dimension(150, 40));
        knappPanel.add(venstreKnapp, BorderLayout.WEST);

        // Funksjon for venstre-knappen
        class Left implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveWest();
            }
        }
        venstreKnapp.addActionListener(new Left());

        // Knapp høyre
        JButton hoyreKnapp = new JButton("Right");
        hoyreKnapp.setPreferredSize(new Dimension(150, 40));
        knappPanel.add(hoyreKnapp, BorderLayout.EAST);

        // Funksjon for høyre-knappen
        class Right implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveEast();
            }
        }
        hoyreKnapp.addActionListener(new Right());

        // Legger knappepanelet til i hovedpanelet
        hovedPanel.add(knappPanel);


        scoreText = new JLabel("Score: " + snakeModel.score);
        scoreText.setPreferredSize(new Dimension(buttonSize, buttonSize));
        scoreText.setHorizontalAlignment(JLabel.CENTER);
        scoreText.setVerticalAlignment(JLabel.CENTER);
        hovedPanel.add(scoreText, BorderLayout.WEST);
        
        // Avslutt knapp
        JButton avsluttKnapp = new JButton("Quit");
        avsluttKnapp.setPreferredSize(new Dimension(buttonSize, buttonSize));
        hovedPanel.add(avsluttKnapp, BorderLayout.EAST);

        // Avslutt-funksjon for avslutt-knappen
        class Stopper implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Spillet er naa avsluttet, takk for din deltakelse!");
                System.exit(0);
            }
        }
        avsluttKnapp.addActionListener(new Stopper());

        // Rutenettet
        JPanel rutenett = new JPanel();
        rutenett.setLayout(new GridLayout(snakeModel.width, snakeModel.height));
        for (int i = 0; i < snakeModel.width; i++) {
            for (int j = 0; j < snakeModel.height; j++) {
                JLabel rute = new JLabel("");

                rute.setPreferredSize(new Dimension(cellWidth, cellHeight)); // Sett størrelsene til 50
                rute.setHorizontalAlignment(JLabel.CENTER);
                rute.setVerticalAlignment(JLabel.CENTER);
                rute.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                rute.setOpaque(true);
                rute.setBackground(Color.WHITE); // Bakgrunnsfarge for rutenettet

                rutenett.add(rute);
                this.ruter[i][j] = rute;
            }
        }
        hovedPanel.add(rutenett, BorderLayout.SOUTH);

        vindu.pack();
        vindu.setVisible(true);
    }
}
