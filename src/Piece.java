import javax.swing.*;

abstract class Piece {
    ImageIcon icon;
    String name;
    String color;
    int row, col;
    boolean alive = true;

    public Piece(String name, String color, int row, int col, ImageIcon icon) {
        this.name = name;
        this.color = color;
        this.row = row;
        this.col = col;
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return this.icon;
    }

    public String getColor() {
        return this.color;
    }

    abstract boolean isValidMove(int newRow, int newCol, Square[][] board);

    void move(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }
}
