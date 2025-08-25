import java.awt.Color;
import javax.swing.JLabel;

public class Square extends JLabel {
    // what is contain, position, is_empty, set_piece, get_piece, remove_piece.

    private int row;
    private int col;
    String piece = null; // -> will modify to be class after making pieces class.

    public Square(Color c, int row, int col) {
        this.setBackground(c);
        this.setOpaque(true);
        this.row = row;
        this.col = col;
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

    public String getPiece() {
        return piece;
    }

    public void setPiece(String spiece) {
        piece = spiece;
    }

    public void removePiece() {
        piece = null;
    }
}
