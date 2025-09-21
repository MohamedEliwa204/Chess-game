import javax.swing.*;
import java.awt.*;

public class GameManage {
    public static void CheckingState(String opponentColor , Square[][] parent) {

        Piece king = opponentColor.equals("Black") ? GameLogic.Black_King : GameLogic.White_King;

        GameState gameState = GameLogic.WinLoseDrawContinue(parent, opponentColor);

        if (gameState.state == 'C') {
            boolean hasKing = false;
            if (gameState.isInCheck) {
                for (int i = 0; i < GameLogic.possiblePieces.size(); i++) {
                    Piece piece = GameLogic.possiblePieces.get(i);
                    if (piece instanceof King) {
                        parent[king.row][king.col].setBackground(Color.ORANGE);
                        hasKing = true;
                        continue;
                    }
                    parent[piece.row][piece.col].setBackground(Color.YELLOW);
                }
                if (!hasKing) {
                    parent[king.row][king.col].setBackground(Color.RED);
                }
            }

        } else if (gameState.state == 'L') {
            int choice = JOptionPane.showOptionDialog(
                    null,
                    (opponentColor.equals("Black") ? "White" : "Black") + " wins by Checkmate!",
                    "Game Over",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[] { "Play Again", "Quit" },
                    "Play Again");

            if (choice == JOptionPane.YES_OPTION) {
                // reset board
            } else {
                // go back to main menu
            }

        } else {
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Game ended in a draw!",
                    "Draw",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[] { "Play Again", "Quit" },
                    "Play Again");

            if (choice == JOptionPane.YES_OPTION) {
                // reset board
            } else {
                // go back to main menu
            }
        }
    }

    public static Piece isTherePromotion() {
        Square[][] board = Square.parentBoard.board;
        for (int j = 0; j <= 7; j++) {
            if (!board[0][j].isEmpty() && board[0][j].getPiece().name.equals("Pawn")
                    && board[0][j].getPiece().color.equals("Black")) {

                return board[0][j].getPiece();
            }

            if (!board[7][j].isEmpty() && board[7][j].getPiece().name.equals("Pawn")
                    && board[7][j].getPiece().color.equals("White")) {

                return board[7][j].getPiece();
            }

        }
        return null;
    }
}
