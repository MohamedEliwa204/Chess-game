import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Square extends JLabel implements ActionListener {
    // what is contain, position, is_empty, set_piece, get_piece, remove_piece.

    Piece piece; // -> will modify to be class after making pieces class.
    JButton button;
    int row;
    int col;
    Color c;

    public Square(Color c, int row, int col) {

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
        this.c = c;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece spiece) {
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
