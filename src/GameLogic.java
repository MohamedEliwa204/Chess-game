import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GameLogic {

    private static final int IMAGE_SIZE = 60;

   static ArrayList<Piece> possiblePieces = new ArrayList<>();
   static Piece White_King ;
   static Piece Black_King;
    public static boolean isInCheck(String KingColor, Board board) {
        int row = -1, col = -1;
        Square[][] Grid = board.board;
        // Here i am searching for the king position in the board
        outer: for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = Grid[i][j].getPiece();
                if (piece != null && piece instanceof King && piece.color.equals(KingColor)) {
                    // finally i found the king
                    row = i;
                    col = j;
                    if(KingColor.equals("Black")){
                        Black_King=Grid[i][j].getPiece();
                    }
                    else{
                        White_King=Grid[i][j].getPiece();
                    }
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
    // overloaded method for the King
    public static boolean isInCheck(String kingColor, Square[][] grid, int kingRow, int kingCol) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = grid[i][j].getPiece();
                if (piece != null && !piece.color.equals(kingColor) && piece.isValidMove(kingRow, kingCol, grid)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void PawnPromote(Pawn pawn, Board board) {
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

            ImageIcon queenImage = pawn.color.equals("White") ? new ImageIcon(GameLogic.class.getResource("/pieces/queen_W.png"))
                    : new ImageIcon(GameLogic.class.getResource("/pieces/queen_B.png"));
            ImageIcon bishopImage = pawn.color.equals("White") ? new ImageIcon(GameLogic.class.getResource("/pieces/bishop_W.png"))
                    : new ImageIcon(GameLogic.class.getResource("/pieces/bishop_B.png"));
            ImageIcon rookImage = pawn.color.equals("White") ? new ImageIcon(GameLogic.class.getResource("/pieces/rook_W.png"))
                    : new ImageIcon(GameLogic.class.getResource("/pieces/rook_B.png"));
            ImageIcon knightImage = pawn.color.equals("White") ? new ImageIcon(GameLogic.class.getResource("/pieces/Knight_W.png"))
                    : new ImageIcon(GameLogic.class.getResource("/pieces/Knight_B.png"));

            switch (choice) {
                case 0:
                    Grid[pawn.row][pawn.col].removePiece();
                    Grid[pawn.row][pawn.col].setPiece(new Queen(pawn.color, pawn.row, pawn.col, scaleImage(queenImage,IMAGE_SIZE,IMAGE_SIZE)));
                    Grid[pawn.row][pawn.col].repaint();
                    break;
                case 1:
                    Grid[pawn.row][pawn.col].removePiece();
                    Grid[pawn.row][pawn.col].setPiece(new Bishop(pawn.color, pawn.row, pawn.col, scaleImage(bishopImage,IMAGE_SIZE,IMAGE_SIZE)));
                    Grid[pawn.row][pawn.col].repaint();

                    break;
                case 2:
                    Grid[pawn.row][pawn.col].removePiece();
                    Grid[pawn.row][pawn.col].setPiece(new Rook(pawn.color, pawn.row, pawn.col, scaleImage(rookImage,IMAGE_SIZE,IMAGE_SIZE)));
                    Grid[pawn.row][pawn.col].repaint();

                    break;
                case 3:
                    Grid[pawn.row][pawn.col].removePiece();
                    Grid[pawn.row][pawn.col].setPiece(new Knight(pawn.color, pawn.row, pawn.col, scaleImage(knightImage,IMAGE_SIZE,IMAGE_SIZE)));
                    Grid[pawn.row][pawn.col].repaint();

                    break;
            }
        }
    }

    public static GameState WinLoseDrawContinue(Board board, String TurnColor) {
        GameState gameState = new GameState();
        possiblePieces.clear();
        gameState.isInCheck = isInCheck(TurnColor, board);
        Square[][] Grid = board.board;

        if (gameState.isInCheck) {
            boolean hasEscape = false;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j <= 7; j++) {
                    Piece myPiece = Grid[i][j].getPiece();
                    if (myPiece != null && myPiece.color.equals(TurnColor)) {
                        for (int r = 0; r <=7; r++) {
                            for (int c = 0; c <= 7; c++) {
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
            gameState.state = hasEscape ? 'C' : 'L';
            return gameState;
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Piece myPiece = Grid[i][j].getPiece();
                    if (myPiece != null && myPiece.color.equals(TurnColor)) {
                        for (int r = 0; r < 8; r++) {
                            for (int c = 0; c < 8; c++) {
                                if (myPiece.isValidMove(r, c, Grid)) {
                                    gameState.state ='C';
                                    return gameState;
                                }
                            }
                        }
                    }
                }
            }
            gameState.state ='D';
            return gameState;
        }
    }

    private static ImageIcon scaleImage(ImageIcon icon, int w, int h) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }

}
