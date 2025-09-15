import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MediumEngine implements ChessEngine {

    @Override
    public Move findBestMove(Square[][] board, String aiColor) {
        List<WeightedMove> allPossibleMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board[i][j].isEmpty() && board[i][j].getPiece().getColor().equals(aiColor)) {
                    Piece piece = board[i][j].getPiece();
                    for (int r = 0; r < 8; r++) {
                        for (int c = 0; c < 8; c++) {
                            if (piece.isValidMove(r, c, board)) {
                                Piece capturedPiece = board[r][c].getPiece();
                                if (capturedPiece == null) {
                                    allPossibleMoves
                                            .add(new WeightedMove(0, new Move(piece, i, j, r, c, capturedPiece)));
                                } else if (capturedPiece.name.equals("Queen")) {
                                    allPossibleMoves
                                            .add(new WeightedMove(9, new Move(piece, i, j, r, c, capturedPiece)));
                                } else if (capturedPiece.name.equals("Rook")) {
                                    allPossibleMoves
                                            .add(new WeightedMove(5, new Move(piece, i, j, r, c, capturedPiece)));
                                } else if (capturedPiece.name.equals("Bishop")) {
                                    allPossibleMoves
                                            .add(new WeightedMove(3, new Move(piece, i, j, r, c, capturedPiece)));
                                } else if (capturedPiece.name.equals("Knight")) {
                                    allPossibleMoves
                                            .add(new WeightedMove(3, new Move(piece, i, j, r, c, capturedPiece)));
                                } else if (capturedPiece.name.equals("Pawn")) {
                                    allPossibleMoves
                                            .add(new WeightedMove(1, new Move(piece, i, j, r, c, capturedPiece)));
                                }
                            }
                        }
                    }
                }
            }
        }
        if (allPossibleMoves.isEmpty()) {
            return null;
        }
        int max = Integer.MIN_VALUE;
        for (WeightedMove weightedMove : allPossibleMoves) {
            if (weightedMove.getWeight() > max) {
                max = weightedMove.getWeight();
            }
        }
        List<Move> result = new ArrayList<>();
        for (WeightedMove weightedMove : allPossibleMoves) {
            if (weightedMove.getWeight() == max) {
                result.add(weightedMove.getMove());
            }
        }
        Random rand = new Random();
        return result.get(rand.nextInt(result.size()));
    }

}
