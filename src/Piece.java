import javax.swing.ImageIcon;
import javax.swing.JLabel;

abstract public class Piece {
    boolean team;
    String move;
    ImageIcon icon;
    JLabel label;

    public Piece(boolean team, String move, ImageIcon image) {
        this.team = team;
        this.move = move;

    }

}
