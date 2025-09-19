import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MediumEngine implements ChessEngine {
    private final Random rand = new Random();

    @Override
    public Move findBestMove(Square[][] board, String aiColor) {
        List<WeightedMove> allPossibleMoves = new ArrayList<>();

        // generate all moves
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board[i][j].isEmpty() && board[i][j].getPiece().getColor().equals(aiColor)) {
                    Piece piece = board[i][j].getPiece();

                    for (int r = 0; r < 8; r++) {
                        for (int c = 0; c < 8; c++) {
                            if (piece.isValidMove(r, c, board)) {
                                Piece captured = board[r][c].getPiece();
                                int weight = evaluateCapture(captured);

                                // simulate move on a copy to avoid messing real board
                                Square[][] newBoard = BoardUtils.copyBoard(board);
                                newBoard[r][c].setPiece(piece);
                                newBoard[i][j].setPiece(null);

                                if (!GameLogic.isInCheck(aiColor, newBoard)) {
                                    allPossibleMoves
                                            .add(new WeightedMove(weight, new Move(piece, i, j, r, c, captured)));
                                }
                            }
                        }
                    }
                }
            }
        }

        if (allPossibleMoves.isEmpty()) {
            return null; // checkmate or stalemate
        }

        // find max weight
        int max = allPossibleMoves.stream()
                .mapToInt(WeightedMove::getWeight)
                .max()
                .orElse(0);

        // collect only the best moves
        List<Move> bestMoves = new ArrayList<>();
        for (WeightedMove wm : allPossibleMoves) {
            if (wm.getWeight() == max) {
                bestMoves.add(wm.getMove());
            }
        }

        // return random among best
        return bestMoves.get(rand.nextInt(bestMoves.size()));
    }

    private int evaluateCapture(Piece captured) {
        if (captured == null)
            return 0;
        switch (captured.name) {
            case "Queen":
                return 9;
            case "Rook":
                return 5;
            case "Bishop":
                return 3;
            case "Knight":
                return 3;
            case "Pawn":
                return 1;
            default:
                return 0;
        }
    }
}
