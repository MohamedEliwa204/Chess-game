import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JPanel implements ActionListener {
    private final App controller;
    JButton playervsplayer;
    JButton playervsengine;
    JButton exit;
    JLabel welcome;

    public MainMenu(App controller) {
        this.controller = controller;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.CENTER;
        welcome = new JLabel("Hello in our chess game!");
        welcome.setPreferredSize(new Dimension(450, 150));
        welcome.setBackground(new Color(88, 177, 166));
        welcome.setFont(new Font("MedievalSharp", Font.ITALIC, 40));
        playervsplayer = new JButton("Player VS Player");
        playervsplayer.setBackground(new Color(1, 86, 112));
        playervsplayer.setForeground(new Color(232, 224, 200));
        playervsplayer.setPreferredSize(new Dimension(300, 70));
        playervsplayer.setFocusPainted(false);
        playervsplayer.addActionListener(this);
        playervsplayer.setFont(new Font("SansSerif", Font.BOLD, 25));
        playervsengine = new JButton("Player VS Engine");
        playervsengine.setBackground(new Color(1, 86, 112));
        playervsengine.setForeground(new Color(232, 224, 200));
        playervsengine.setPreferredSize(new Dimension(300, 70));
        playervsengine.setFocusPainted(false);
        playervsengine.addActionListener(this);
        playervsengine.setFont(new Font("SansSerif", Font.BOLD, 25));
        exit = new JButton("EXIT");
        exit.setBackground(new Color(64, 0, 0));
        exit.setForeground(new Color(250, 0, 0));
        exit.setPreferredSize(new Dimension(200, 70));
        exit.setFocusPainted(false);
        exit.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(welcome, gbc);

        gbc.gridy = 1;
        this.add(playervsplayer, gbc);

        gbc.gridy = 2;
        this.add(playervsengine, gbc);

        gbc.gridy = 3;
        this.add(exit, gbc);
        this.setBackground(new Color(88, 177, 166));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            System.exit(0);
        } else if (e.getSource() == playervsplayer) {
            controller.showSetting();

        } else if (e.getSource() == playervsengine) {
            controller.showSetting2();

        }

        // throw new UnsupportedOperationException("Unimplemented method
        // 'actionPerformed'");
    }

}
