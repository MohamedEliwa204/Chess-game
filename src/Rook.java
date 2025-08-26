import javax.swing.*;

public class Rook extends Piece{
    public Rook(String color, int row, int col, ImageIcon icon) {
        super("Rook", color, row, col, icon);
    }

    @Override
    boolean isValidMove(int newRow, int newCol, Piece[][] board) {
        if(newRow>=0 && newRow<=7 && newCol>=0 && newCol<=7){
            if (newCol == col) {
                int step = (newRow > row) ? 1 : -1;
                for (int r = row + step; r != newRow; r += step) {
                    if (board[r][col] != null) {
                        return false;
                    }
                }
                if(board[newRow][newCol].equals(color)){
                    return false;
                }
                return true;
            }
            if(newRow==row){
                int step = (newCol> col) ? 1 : -1 ;
                for(int c= col + step ; c != newCol ; c+=step){
                    if(board[row][c]!=null){
                        return false;
                    }
                }
                if(board[newRow][newCol].equals(color)){
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    void move(int newRow, int newCol) {
        super.move(newRow, newCol);
    }
}
