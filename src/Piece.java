import javax.swing.ImageIcon;
import javax.swing.JLabel;

abstract public class Piece {
    char color;
    String type;
    ImageIcon icon;
    JLabel label;

    // position, is_valid_move
    public Piece(char color, String type) {
        this.color = color;
        this.type = type;

    }

}
