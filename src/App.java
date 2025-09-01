import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class App implements ActionListener {
    private final CardLayout cardLayout;
    JFrame mainFrame;
    JPanel cardPanel;
    MainMenu mainMenu;
    SettingMenu settingMenu;
    Board board;
    JFrame level;
    JRadioButton easy;
    JRadioButton medium;
    JRadioButton hard;
    JRadioButton legend;
    // JButton ok;

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
        cardLayout.show(cardPanel, "Play board");
    }

    public void showSetting() {

        cardLayout.show(cardPanel, "Setting Menu");
    }

    public void setLevel() {
        level = new JFrame();
        level.setTitle("level of engine!");
        level.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        level.setLayout(new FlowLayout());
        level.setSize(300, 300);
        easy = new JRadioButton("Easy!");
        medium = new JRadioButton("Medium!");
        hard = new JRadioButton("Hard!");
        legend = new JRadioButton("Legend!");

        // ok = new JButton("OK");
        // ok.setPreferredSize(new Dimension(50, 50));
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        legend.addActionListener(this);
        ButtonGroup group = new ButtonGroup();

        group.add(easy);
        group.add(medium);
        group.add(hard);
        group.add(legend);

        level.add(easy);
        level.add(medium);
        level.add(hard);
        level.add(legend);
        level.pack();
        level.setVisible(true);

    }

    public static void main(String[] args) throws Exception {

        new App();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == easy) {
            level.dispose();

        } else if (e.getSource() == medium) {
            level.dispose();
        } else if (e.getSource() == hard) {
            level.dispose();
        } else if (e.getSource() == legend) {
            level.dispose();
        }

    }
}
