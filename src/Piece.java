import java.util.Stack;

import javax.swing.*;

abstract class Piece {
    ImageIcon icon;
    String name;
    String color;
    int row, col;
    boolean alive = true;
    Stack<Integer> parentRow, parentCol;

    public Piece(String name, String color, int row, int col, ImageIcon icon) {
        this.name = name;
        this.color = color;
        this.row = row;
        this.col = col;
        parentCol = new Stack<>();
        parentRow = new Stack<>();
        this.icon = icon;
    }

    // public void setParent(int r, int c) {
    // this.parentCol = c;
    // this.parentRow = r;
    // }

    public ImageIcon getIcon() {
        return this.icon;
    }

    public String getColor() {
        return this.color;
    }

    abstract boolean isValidMove(int newRow, int newCol, Square[][] board);

    void move(int newRow, int newCol) {
        this.parentCol.add(col);
        this.parentRow.add(row);
        this.row = newRow;
        this.col = newCol;
    }
}
