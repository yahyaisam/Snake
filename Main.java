import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }

        catch (Exception e) {
            System.exit(1);
        }

        SnakeModel model = new SnakeModel(12, 12);
        SnakeGUI gui = new SnakeGUI(model);
        
        gui.initApples();
        // Game loop
        while(model.playing) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            model.updateMove();

            if (model.playing) {
                gui.updateGUI();
            }
        }
    }
}
