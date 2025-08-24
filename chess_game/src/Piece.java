import javax.swing.JLabel;
import javax.swing.ImageIcon;

abstract public class Piece {
    boolean team;
    String move;
    ImageIcon icon;
    JLabel label;

    public Piece(boolean team, String move, ImageIcon image) {
        this.team = team;
        this.move = move;
        label.setIcon(image);
    }

}
