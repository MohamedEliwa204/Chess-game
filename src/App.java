import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class App {
    JFrame mainFrame;
    JPanel cardPanel;

    public App() {
        mainFrame = new JFrame("Elshewy&Gabr Chess Game!");

        cardPanel = new JPanel(new CardLayout());
    }

    public static void main(String[] args) throws Exception {

        new Board();
    }
}
