import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyEngine implements ChessEngine {
    private final Random rand = new Random();

    @Override
    public Move findBestMove(Square[][] board, String aiColor) {
        List<Move> allPossibleMoves = new ArrayList<>();

        // Generate all moves for AI pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board[i][j].isEmpty() && board[i][j].getPiece().getColor().equals(aiColor)) {
                    Piece piece = board[i][j].getPiece();

                    for (int r = 0; r < 8; r++) {
                        for (int c = 0; c < 8; c++) {
                            if (piece.isValidMove(r, c, board)) {
                                // simulate move on a copy of the board
                                Square[][] newBoard = BoardUtils.copyBoard(board);
                                Piece captured = newBoard[r][c].getPiece();
                                newBoard[r][c].setPiece(piece);
                                newBoard[i][j].setPiece(null);

                                if (!GameLogic.isInCheck(aiColor, newBoard)) {
                                    allPossibleMoves.add(new Move(piece, i, j, r, c, captured));
                                }
                            }
                        }
                    }
                }
            }
        }

        // If no moves available, return null (checkmate/stalemate)
        if (allPossibleMoves.isEmpty()) {
            return null;
        }

        // Pick a random move
        return allPossibleMoves.get(rand.nextInt(allPossibleMoves.size()));
    }
}
