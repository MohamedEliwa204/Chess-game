import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GameLogic {

    ArrayList<Piece> possiblePieces = new ArrayList<>();

    public boolean isInCheck(String KingColor, Board board) {
        int row = -1, col = -1;
        Square[][] Grid = board.board;
        outer: for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = Grid[i][j].getPiece();
                if (piece != null && piece instanceof King && piece.color.equals(KingColor)) {
                    row = i;
                    col = j;
                    break outer;
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = Grid[i][j].getPiece();
                if (piece != null && !piece.color.equals(KingColor) && piece.isValidMove(row, col, Grid)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void PawnPromote(Pawn pawn, Board board) {
        Square[][] Grid = board.board;
        if ((pawn.color.equals("Black") && pawn.row == 0) ||
                (pawn.color.equals("White") && pawn.row == 7)) {
            String[] options = { "Queen", "Bishop", "Rook", "Knight" };
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Promote pawn to:",
                    "Pawn Promotion",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            // here i don't have the paths yet so i wrote anything
            ImageIcon queenImage = pawn.color.equals("White") ? new ImageIcon("white_queen.png")
                    : new ImageIcon("black_queen.png");
            ImageIcon bishopImage = pawn.color.equals("White") ? new ImageIcon("white_bishop.png")
                    : new ImageIcon("black_bishop.png");
            ImageIcon rookImage = pawn.color.equals("White") ? new ImageIcon("white_rook.png")
                    : new ImageIcon("black_rook.png");
            ImageIcon knightImage = pawn.color.equals("White") ? new ImageIcon("white_knight.png")
                    : new ImageIcon("black_knight.png");

            switch (choice) {
                case 0:
                    Grid[pawn.row][pawn.col].setPiece(new Queen(pawn.color, pawn.row, pawn.col, queenImage));
                    break;
                case 1:
                    Grid[pawn.row][pawn.col].setPiece(new Bishop(pawn.color, pawn.row, pawn.col, bishopImage));
                    break;
                case 2:
                    Grid[pawn.row][pawn.col].setPiece(new Rook(pawn.color, pawn.row, pawn.col, rookImage));
                    break;
                case 3:
                    Grid[pawn.row][pawn.col].setPiece(new Knight(pawn.color, pawn.row, pawn.col, knightImage));
                    break;
            }
        }
    }

    public char WinLoseDrawContinue(Board board, String TurnColor) {
        possiblePieces.clear();
        boolean isCheck = isInCheck(TurnColor, board);
        Square[][] Grid = board.board;

        if (isCheck) {
            boolean hasEscape = false;
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    Piece myPiece = Grid[i][j].getPiece();
                    if (myPiece != null && myPiece.color.equals(TurnColor)) {
                        for (int r = 1; r <= 8; r++) {
                            for (int c = 1; c <= 8; c++) {
                                if (myPiece.isValidMove(r, c, Grid)) {
                                    Piece captured = Grid[r][c].getPiece();
                                    Grid[i][j].setPiece(null);
                                    Grid[r][c].setPiece(myPiece);
                                    if (!isInCheck(myPiece.color, board)) {
                                        hasEscape = true;
                                        possiblePieces.add(myPiece);
                                    }
                                    Grid[i][j].setPiece(myPiece);
                                    Grid[r][c].setPiece(captured);
                                }
                            }
                        }
                    }
                }
            }
            return hasEscape ? 'C' : 'L';
        } else {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    Piece myPiece = Grid[i][j].getPiece();
                    if (myPiece != null && myPiece.color.equals(TurnColor)) {
                        for (int r = 1; r <= 8; r++) {
                            for (int c = 1; c <= 8; c++) {
                                if (myPiece.isValidMove(r, c, Grid)) {
                                    return 'C';
                                }
                            }
                        }
                    }
                }
            }
            return 'D';
        }
    }

}
