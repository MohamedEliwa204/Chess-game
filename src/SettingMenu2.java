import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class SettingMenu2 extends JPanel implements ActionListener {
    private final App controller;
    JButton start;

    JTextField text1;

    JSpinner spinnerTime;
    JLabel label1;

    JLabel label3;
    JButton back;

    public SettingMenu2(App controller) {
        this.controller = controller;
        this.setLayout(new GridBagLayout());
        back = new JButton("Back");
        back.setFocusPainted(false);
        back.setFont(new Font("Dialog", Font.BOLD, 18));
        back.setBounds(0, 0, 50, 50);
        back.addActionListener(this);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(back, gbc);
        gbc.insets = new Insets(20, 5, 20, 5); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        start = new JButton("Start Game!");
        start.setFocusPainted(false);
        start.setBackground(new Color(13, 139, 160));
        start.setForeground(new Color(243, 241, 225));
        start.addActionListener(this);
        label1 = new JLabel("Player 1 name: ");
        label1.setBackground(new Color(60, 69, 80));
        label1.setForeground(Color.WHITE);
        label1.setOpaque(true);
        label1.setPreferredSize(new Dimension(250, 50));
        label1.setFont(new Font("Serif", Font.BOLD, 24));
        label1.setHorizontalAlignment(SwingConstants.TRAILING);

        text1 = new JTextField();
        text1.setPreferredSize(new Dimension(250, 50));
        text1.setFont(new Font("Serif", Font.ITALIC, 24));

        text1.setBackground(new Color(176, 184, 193));

        label3 = new JLabel("set the time(in minutes): ");
        label3.setBackground(new Color(60, 69, 80));
        label3.setForeground(Color.WHITE);
        label3.setOpaque(true);
        label3.setFont(new Font("Serif", Font.BOLD, 24));
        label3.setHorizontalAlignment(SwingConstants.TRAILING);
        spinnerTime = new JSpinner(new SpinnerNumberModel(10, 1, 240, 1));

        JComponent editor = spinnerTime.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField spinnerTextField = ((JSpinner.DefaultEditor) editor).getTextField();
            spinnerTextField.setBackground(new Color(176, 184, 193));
            spinnerTextField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            spinnerTextField.setHorizontalAlignment(JTextField.CENTER);
        }
        start.setFont(new Font("Georgia", Font.BOLD, 18));
        start.setPreferredSize(new Dimension(300, 70));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(label1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(text1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(label3, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(spinnerTime, gbc);
        gbc.gridx = 0;

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(start, gbc);
        this.setBackground(new Color(0, 126, 149));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            String n1 = text1.getText();
            String n2 = "Engine";
            int t = (int) spinnerTime.getValue();
            controller.startGame(n1, n2, t);

        }
        if (e.getSource() == back) {
            controller.backToMain();
        }
        // throw new UnsupportedOperationException("Unimplemented method
        // 'actionPerformed'");
    }

}
