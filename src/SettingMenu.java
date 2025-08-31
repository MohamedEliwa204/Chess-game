import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class SettingMenu extends JPanel implements ActionListener {
    JButton start;

    JTextField text1;
    JTextField text2;
    JSpinner spinnerTime;
    JLabel label1;
    JLabel label2;
    JLabel label3;

    public SettingMenu() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        start = new JButton("Start Game!");
        start.setFocusPainted(false);
        start.addActionListener(this);
        label1 = new JLabel("Player 1 name: ");

        label2 = new JLabel("Player 2 name: ");
        text1 = new JTextField();
        text1.setPreferredSize(new Dimension(250, 70));
        text2 = new JTextField();
        text2.setPreferredSize(new Dimension(250, 70));

        label3 = new JLabel("set the time(in minutes): ");
        spinnerTime = new JSpinner(new SpinnerNumberModel(10, 1, 240, 1));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(label1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(text1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(label2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(text2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(label3, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(spinnerTime, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(start);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {

        }
        // throw new UnsupportedOperationException("Unimplemented method
        // 'actionPerformed'");
    }

}
