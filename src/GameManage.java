import javax.swing.*;
import java.awt.*;

public class GameManage {
    public static void CheckingState(String opponentColor){
        Board parent = Square.parentBoard;
        Piece king = opponentColor.equals("Black") ? GameLogic.Black_King : GameLogic.White_King;

        GameState gameState = GameLogic.WinLoseDrawContinue(parent, opponentColor);

        if (gameState.state == 'C') {
            boolean hasKing = false;
            if (gameState.isInCheck) {
                for (int i = 0; i < GameLogic.possiblePieces.size(); i++) {
                    Piece piece = GameLogic.possiblePieces.get(i);
                    if (piece instanceof King) {
                        parent.board[king.row][king.col].setBackground(Color.ORANGE);
                        hasKing = true;
                        continue;
                    }
                    parent.board[piece.row][piece.col].setBackground(Color.YELLOW);
                }
                if (!hasKing) {
                    parent.board[king.row][king.col].setBackground(Color.RED);
                }
            } else {
                parent.clear_add_color();
            }

        } else if (gameState.state == 'L') {
            int choice = JOptionPane.showOptionDialog(
                    null,
                    (opponentColor.equals("Black") ? "White" : "Black") + " wins by Checkmate!",
                    "Game Over",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Play Again", "Quit"},
                    "Play Again"
            );

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
                    new String[]{"Play Again", "Quit"},
                    "Play Again"
            );

            if (choice == JOptionPane.YES_OPTION) {
                // reset board
            } else {
                // go back to main menu
            }
        }
    }
}
