import java.util.ArrayList;
import java.util.List;

public class HardEngine implements ChessEngine {
    final int INF = Integer.MAX_VALUE;

    public int minimax(Square[][] board, int depth, boolean maximizing) {
        if (depth == 0) {
            // return evaluate(board);
        }
        /*
         * if(game over && maximizing){
         * return INF;
         * }
         * if(game over && !maximizing){
         * return -INF;
         * }
         * 
         */
        if (maximizing) {
            int maxEval = -INF;
            for (Move move : legalMoves(board, maximizing)) {
                int eval = minimax(boardAfterMove(board, move), depth - 1, false);

                maxEval = Math.max(eval, maxEval);

            }

            return maxEval;

        } else {
            int minEval = INF;
            for (Move move : legalMoves(board, maximizing)) {
                int eval = minimax(boardAfterMove(board, move), depth - 1, true);
                minEval = Math.min(eval, minEval);
            }
            return minEval;
        }

    }

    private int evaluateBoard(Square[][] board) {
        return (Integer) null;
    }

    private List<Move> legalMoves(Square[][] board, boolean maximizing) {
        List<Move> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (maximizing) {
                    if (!board[i][j].isEmpty() && board[i][j].getPiece().color.equals("Black")) {
                        Piece piece = board[i][j].getPiece();
                        for (int r = 0; r < 8; r++) {
                            for (int c = 0; c < 8; c++) {
                                if (piece.isValidMove(r, c, board)) {
                                    Piece captured = board[r][c].getPiece();
                                    result.add(new Move(piece, i, j, r, c, captured));
                                }
                            }
                        }
                    }
                } else {
                    if (!board[i][j].isEmpty() && board[i][j].getPiece().color.equals("White")) {
                        Piece piece = board[i][j].getPiece();
                        for (int r = 0; r < 8; r++) {
                            for (int c = 0; c < 8; c++) {
                                if (piece.isValidMove(r, c, board)) {
                                    Piece captured = board[r][c].getPiece();
                                    result.add(new Move(piece, i, j, r, c, captured));
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private Square[][] boardAfterMove(Square[][] originalBoard, Move move) {
        Square[][] newBoard = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoard[i][j] = new Square(null, originalBoard[i][j].c, i, j);
                if (!originalBoard[i][j].isEmpty()) {
                    newBoard[i][j].setPiece(originalBoard[i][j].getPiece().clone());
                }

            }

        }
        Piece pieceToMove = newBoard[move.startRow][move.startCol].getPiece();
        newBoard[move.startRow][move.startCol].removePiece();
        newBoard[move.endRow][move.endCol].setPiece(pieceToMove);

        return newBoard;

    }

    @Override
    public Move findBestMove(Square[][] board, String aiColor) {
        boolean maximizing = true;
        int bestVal = -INF;
        Move bestMove = null;

        for (Move move : legalMoves(board, maximizing)) {
            int eval = minimax(boardAfterMove(board, move), 10, !maximizing);
            if (eval > bestVal) {
                bestVal = eval;
                bestMove = move;
            }
        }

        return bestMove;
    }
}
