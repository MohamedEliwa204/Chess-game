import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyEngine implements ChessEngine {

    @Override
    public Move findBestMove(Square[][] board, String aiColor) {
        List<Move> allPossibleMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board[i][j].isEmpty() && board[i][j].getPiece().getColor().equals(aiColor)) {
                    Piece piece = board[i][j].getPiece();
                    for (int r = 0; r < 8; r++) {
                        for (int c = 0; c < 8; c++) {
                            if (piece.isValidMove(r, c, board)) {
                                Piece capturedPiece = board[r][c].getPiece();
                                allPossibleMoves.add(new Move(piece, i, j, r, c, capturedPiece));
                            }
                        }
                    }
                }
            }
        }
        if (allPossibleMoves.isEmpty()) {
            return null;
        }
        Random rand = new Random();
        return allPossibleMoves.get(rand.nextInt(allPossibleMoves.size()));
    }

}
