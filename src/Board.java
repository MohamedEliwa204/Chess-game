import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board extends JPanel {
    // initialize board
    // move piece
    // display board
    JFrame frame;

    public Board() {

        this.setLayout(new GridLayout(8, 8));

        for (int i = 0; i < 8; i++) {
            char temp = 'a';
            for (int j = 0; j < 8; j++) {
                Square square;
                if ((i + j) % 2 == 0) {
                    square = new Square(Color.WHITE, (char) ((i + 1) + '0'), temp);
                } else {
                    square = new Square(Color.BLACK, (char) ((i + 1) + '0'), temp);
                }
                this.add(square);
                temp += 1;
            }

        }
        frame = new JFrame();
        ImageIcon icon = new ImageIcon();

        frame.setTitle("Hello in our chess game!");
        frame.setResizable(true);
        frame.setSize(420, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(this);

    }

}
