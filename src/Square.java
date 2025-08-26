import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Square extends JLabel implements ActionListener {
    // what is contain, position, is_empty, set_piece, get_piece, remove_piece.

    String piece = null; // -> will modify to be class after making pieces class.
    JButton button;

    public Square(Color c) {

        button = new JButton();
        button.setBounds(this.getBounds());

        // to make the button with no animation
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);

        this.add(button);
        this.setBackground(c);
        this.setOpaque(true);

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
        }
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
