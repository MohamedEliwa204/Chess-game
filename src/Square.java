import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Square extends JLabel implements ActionListener {
    // what is contain, position, is_empty, set_piece, get_piece, remove_piece.

    String piece = null; // -> will modify to be class after making pieces class.
    JButton button;
    char row;
    char col;

    public Square(Color c, char row, char col) {

        button = new JButton();
        this.setLayout(new BorderLayout());
        this.row = row;
        this.col = col;
        // to make the button with no animation
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(this);
        this.add(button, BorderLayout.CENTER);
        this.setBackground(c);
        this.setOpaque(true);

    }

    public char getRow() {
        return row;
    }

    public char getCol() {
        return col;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String spiece) {
        piece = spiece;
    }

    public void removePiece() {
        piece = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            // will be implemented
            System.out.printf("hello in my square! (%c,%c)%n", row, col);
        }
        // throw new UnsupportedOperationException("Unimplemented method
        // 'actionPerformed'");
    }
}
