import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App {
    private final CardLayout cardLayout;
    JFrame mainFrame;
    JPanel cardPanel;
    MainMenu mainMenu;
    SettingMenu settingMenu;
    Board board;

    public App() {
        mainFrame = new JFrame("Elshewy&Gabr Chess Game!");
        ImageIcon icon = new ImageIcon(getClass().getResource("/pieces/king-.jpg"));
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH);
        mainFrame.setLayout(new BorderLayout(0, 0));
        mainFrame.setIconImage(resized);
        mainFrame.setResizable(true);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainMenu = new MainMenu(this);
        settingMenu = new SettingMenu(this);
        board = new Board();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(mainMenu, "Main menu");
        cardPanel.add(settingMenu, "Setting Menu");
        cardPanel.add(board, "Play board");

        mainFrame.add(cardPanel);
        mainFrame.setVisible(true);
    }

    public void startGame(String n1, String n2, int t) {
        board.setupGame(n1, n2, t);
        cardLayout.show(cardPanel, "Play Board");
    }

    public static void main(String[] args) throws Exception {

        new Board();
    }
}
