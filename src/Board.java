import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Board extends JPanel {
    // initialize board
    // move piece
    // display board
    JFrame frame;

    public Board() {

        this.setLayout(new GridLayout(8, 8));
        JPanel leftPanel = new JPanel(new GridLayout(8, 1));
        JPanel bottomPanel = new JPanel(new GridLayout(1, 8));
        for (int i = 1; i <= 8; i++) {
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            leftPanel.add(label);
        }
        for (char i = 'a'; i <= 'h'; i++) {
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            bottomPanel.add(label);
        }
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
        frame.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("C:\\Users\\Mohamed\\Downloads\\king-1716907_640.jpg");
        frame.setIconImage(icon.getImage());
        frame.setTitle("Hello in our chess game!");
        frame.setResizable(true);
        frame.setSize(420, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(this, BorderLayout.CENTER);

    }

}
