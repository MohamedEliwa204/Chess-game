import java.util.ArrayList;
import java.util.List;

public class HardEngine implements ChessEngine {
    final int INF = Integer.MAX_VALUE;

    public int minimax(Square[][] board, int depth, int alpha, int beta, boolean maximizing) {
        if (depth == 0) {
            return (int) Math.round(evaluateBoard(board));
        }
        if (GameLogic.WinLoseDrawContinue(board, maximizing ? "Black" : "White").state == 'L') {
            return maximizing ? -1000 : 1000;
        }

        if (maximizing) {
            int maxEval = -INF;
            for (Move move : legalMoves(board, maximizing)) {
                int eval = minimax(boardAfterMove(board, move).board, alpha, beta, depth - 1, false);
                alpha = Math.max(eval, alpha);
                if (beta <= alpha) {
                    break;
                }
                maxEval = Math.max(eval, maxEval);

            }

            return maxEval;

        } else {
            int minEval = INF;
            for (Move move : legalMoves(board, maximizing)) {
                int eval = minimax(boardAfterMove(board, move).board, alpha, beta, depth - 1, true);
                beta = Math.min(eval, beta);
                if (beta <= alpha) {
                    break;
                }
                minEval = Math.min(eval, minEval);
            }
            return minEval;
        }

    }

    private double GivePieceValue(Piece piece) {
        if (piece instanceof Pawn) {
            return 1;
        } else if (piece instanceof Knight) {
            return 3;
        } else if (piece instanceof Bishop) {
            return 3.25;
        } else if (piece instanceof Rook) {
            return 5;
        } else if (piece instanceof Queen) {
            return 9;
        } else if (piece instanceof King) {

            return 0.0;
        } else {
            throw new IllegalArgumentException("Unknown piece type");
        }
    }

    private double MaterialBalance(Square[][] board) {
        double score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board[i][j].isEmpty()) {
                    if (board[i][j].getPiece().color.equals("Black")) {
                        score += GivePieceValue(board[i][j].getPiece());
                    } else {
                        score -= GivePieceValue(board[i][j].getPiece());
                    }
                }
            }
        }
        return score;
    }

    private double KingSafety(BoardState state, boolean isEndGame) {
        double score = 0;

        // Check detection
        if (GameLogic.isInCheck(state.blackKing.color, state.board, state.blackKing.row, state.blackKing.col)) {
            score -= 1000;
        }
        if (GameLogic.isInCheck(state.whiteKing.color, state.board, state.whiteKing.row, state.whiteKing.col)) {
            score += 1000;
        }

        // Castling bonus
        if (state.blackKing.hasCastled) {
            score += 0.5;
        }
        if (state.whiteKing.hasCastled) {
            score -= 0.5;
        }

        // Pawn shield
        score += evaluatePawnShield(state.blackKing, state.board, true);
        score -= evaluatePawnShield(state.whiteKing, state.board, false);

        // Open file / diagonal exposure
        score += evaluateExposure(state.blackKing, state.board, true);
        score -= evaluateExposure(state.whiteKing, state.board, false);

        // Game phase adjustment
        if (!isEndGame) { // Opening/Middlegame
            score += kingCenterPenalty(state.blackKing, true);
            score -= kingCenterPenalty(state.whiteKing, false);
        } else { // Endgame → الملك في النص أفضل
            score -= kingCenterPenalty(state.blackKing, true);
            score += kingCenterPenalty(state.whiteKing, false);
        }

        // Threat proximity
        score += evaluateThreatsNearKing(state.blackKing, state.board, true);
        score -= evaluateThreatsNearKing(state.whiteKing, state.board, false);

        // The King has escape squares or not
        score += evaluateEscapeSquares(state.blackKing, state.board);
        score -= evaluateEscapeSquares(state.whiteKing, state.board);

        return score;
    }

    private double evaluateEscapeSquares(King king, Square[][] board) {
        int row = king.row;
        int col = king.col;
        int[] dr = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dc = { -1, 0, 1, -1, 1, -1, 0, 1 };

        int safeSquares = 0;

        for (int i = 0; i < 8; i++) {
            int r = row + dr[i];
            int c = col + dc[i];
            if (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (board[r][c].isEmpty() || !board[r][c].getPiece().color.equals(king.color)) {
                    safeSquares++;
                }
            }
        }

        if (safeSquares == 0) {
            return -0.5;
        }
        return 0.0;
    }

    private double evaluateThreatsNearKing(King king, Square[][] board, boolean isBlack) {
        double score = 0.0;
        int row = king.row;
        int col = king.col;

        for (int r = Math.max(0, row - 2); r <= Math.min(7, row + 2); r++) {
            for (int c = Math.max(0, col - 2); c <= Math.min(7, col + 2); c++) {
                Piece p = board[r][c].getPiece();
                if (p != null && !p.color.equals(king.color)) {
                    if (p instanceof Queen)
                        score -= 0.5;
                    else if (p instanceof Rook)
                        score -= 0.4;
                    else if (p instanceof Bishop || p instanceof Knight)
                        score -= 0.3;
                }
            }
        }

        return score;
    }

    private double evaluatePawnShield(King king, Square[][] board, boolean isBlack) {
        double score = 0.0;

        int row = king.row;
        int col = king.col;

        int dir = isBlack ? -1 : 1;
        int shieldRow = row + dir;

        if (shieldRow < 0 || shieldRow > 7) {
            return score;
        }

        for (int c = col - 1; c <= col + 1; c++) {
            if (c >= 0 && c < 8) {
                Piece p = board[shieldRow][c].getPiece();

                if (p == null || !(p instanceof Pawn) || !p.color.equals(king.color)) {
                    score -= 0.3; // penalty
                }
            }
        }

        return score;
    }

    private double evaluateExposure(King king, Square[][] board, boolean isBlack) {
        double score = 0.0;
        int row = king.row;
        int col = king.col;

        boolean pawnOnFile = false;
        for (int r = 0; r < 8; r++) {
            Piece p = board[r][col].getPiece();
            if (p != null && p instanceof Pawn && p.color.equals(king.color)) {
                pawnOnFile = true;
                break;
            }
        }
        if (!pawnOnFile)
            score -= 0.4;

        int[] dr = { -1, -1, 1, 1 };
        int[] dc = { -1, 1, -1, 1 };
        for (int d = 0; d < 4; d++) {
            int r = row + dr[d];
            int c = col + dc[d];
            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                Piece p = board[r][c].getPiece();
                if (p != null) {
                    if (p instanceof Pawn && p.color.equals(king.color)) {
                        break;
                    }
                    if ((p instanceof Bishop || p instanceof Queen) && !p.color.equals(king.color)) {
                        score -= 0.3;
                    }
                    break;
                }
                r += dr[d];
                c += dc[d];
            }
        }

        return score;
    }

    private double kingCenterPenalty(King king, boolean isBlack) {
        int row = king.row;
        int col = king.col;

        if ((row >= 3 && row <= 4) && (col >= 3 && col <= 4)) {
            return -0.5;
        }
        return 0.0;
    }

    private double Mobility(Square[][] board, boolean isBlack) {
        double mobility = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board[i][j].isEmpty() && board[i][j].getPiece().color.equals(isBlack ? "Black" : "White")) {
                    Piece piece = board[i][j].getPiece();
                    for (int r = 0; r < 8; r++) {
                        for (int c = 0; c < 8; c++) {
                            if (piece.isValidMove(r, c, board)) {
                                mobility++;
                            }
                        }
                    }
                }
            }
        }
        return mobility * 0.1;
    }

    private double PST(Square[][] board) {
        double score = 0;
        boolean isEndGame = GamePhase.isEndgame(board);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j].getPiece();
                if (piece != null) {
                    // flip row لو القطعة بيضاء
                    int pstRow = piece.color.equals("White") ? 7 - i : i;

                    double value = PieceSquareTables.getPieceSquareValue(
                            piece, pstRow, j, piece.color.equals("Black"), isEndGame);

                    if (piece.color.equals("Black")) {
                        score += value; // AI
                    } else {
                        score -= value; // Opponent
                    }
                }
            }
        }

        return score;
    }

    private double evaluatePassedPawns(Square[][] board) {
        double score = 0.0;
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Piece p = board[row][col].getPiece();
                if (p instanceof Pawn) {
                    boolean isBlack = p.color.equals("Black");
                    int dir = isBlack ? -1 : 1;
                    boolean passed = true;

                    for (int r = row + dir; r >= 0 && r < 8; r += dir) {
                        for (int c = Math.max(0, col - 1); c <= Math.min(7, col + 1); c++) {
                            Piece enemy = board[r][c].getPiece();
                            if (enemy instanceof Pawn && !enemy.color.equals(p.color)) {
                                passed = false;
                            }
                        }
                    }

                    if (passed) {
                        score += isBlack ? 0.5 : -0.5;
                    }
                }
            }
        }
        return score;
    }

    private double evaluateDoubledPawns(Square[][] board) {
        double score = 0.0;
        for (int col = 0; col < 8; col++) {
            int blackCount = 0, whiteCount = 0;
            for (int row = 0; row < 8; row++) {
                Piece p = board[row][col].getPiece();
                if (p instanceof Pawn) {
                    if (p.color.equals("Black"))
                        blackCount++;
                    else
                        whiteCount++;
                }
            }
            if (blackCount > 1)
                score -= 0.25 * (blackCount - 1);
            if (whiteCount > 1)
                score += 0.25 * (whiteCount - 1);
        }
        return score;
    }

    private double evaluateIsolatedPawns(Square[][] board) {
        double score = 0.0;
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Piece p = board[row][col].getPiece();
                if (p instanceof Pawn) {
                    boolean isBlack = p.color.equals("Black");
                    boolean hasNeighbor = false;

                    for (int adj = -1; adj <= 1; adj += 2) {
                        int adjCol = col + adj;
                        if (adjCol >= 0 && adjCol < 8) {
                            for (int r = 0; r < 8; r++) {
                                Piece neighbor = board[r][adjCol].getPiece();
                                if (neighbor instanceof Pawn && neighbor.color.equals(p.color)) {
                                    hasNeighbor = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!hasNeighbor) {
                        score += isBlack ? -0.25 : 0.25;
                    }
                }
            }
        }
        return score;
    }

    private double evaluateBishopPair(Square[][] board) {
        int blackBishops = 0, whiteBishops = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board[i][j].getPiece();
                if (p instanceof Bishop) {
                    if (p.color.equals("Black"))
                        blackBishops++;
                    else
                        whiteBishops++;
                }
            }
        }

        double score = 0.0;
        if (blackBishops >= 2)
            score += 0.5;
        if (whiteBishops >= 2)
            score -= 0.5;

        return score;
    }

    private double evaluateRookFiles(Square[][] board) {
        double score = 0.0;
        for (int col = 0; col < 8; col++) {
            boolean hasBlackPawn = false, hasWhitePawn = false;

            for (int row = 0; row < 8; row++) {
                Piece p = board[row][col].getPiece();
                if (p instanceof Pawn) {
                    if (p.color.equals("Black"))
                        hasBlackPawn = true;
                    else
                        hasWhitePawn = true;
                }
            }

            for (int row = 0; row < 8; row++) {
                Piece p = board[row][col].getPiece();
                if (p instanceof Rook) {
                    if (p.color.equals("Black")) {
                        if (!hasBlackPawn && !hasWhitePawn)
                            score += 0.5; // open file
                        else if (!hasBlackPawn)
                            score += 0.25; // semi-open
                    } else {
                        if (!hasWhitePawn && !hasBlackPawn)
                            score -= 0.5;
                        else if (!hasWhitePawn)
                            score -= 0.25;
                    }
                }
            }
        }
        return score;
    }

    private double CenterControl(Square[][] board) {
        double score = 0.0;

        int[][] centers = {
                { 3, 3 }, { 3, 4 }, // d4, e4
                { 4, 3 }, { 4, 4 } // d5, e5
        };

        for (int[] pos : centers) {
            int r = pos[0];
            int c = pos[1];
            Piece p = board[r][c].getPiece();

            if (p != null) {
                if (p.color.equals("Black")) {
                    score += 0.2;
                } else {
                    score -= 0.2;
                }
            }
        }

        return score;
    }

    private double evaluateBoard(Square[][] board) {
        double score = 0.0;

        BoardState state = new BoardState();
        state.board = board;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board[i][j].getPiece();
                if (p instanceof King) {
                    if (p.color.equals("White")) {
                        state.whiteKing = (King) p;
                    } else {
                        state.blackKing = (King) p;
                    }
                }
            }
        }

        boolean isEndGame = GamePhase.isEndgame(board);

        // 🟢 Material
        score += MaterialBalance(board);

        // 🟢 King safety
        score += KingSafety(state, isEndGame);

        // 🟢 Piece-square tables
        score += PST(board);

        // 🟢 Mobility (Black - White)
        score += Mobility(board, true) - Mobility(board, false);

        // 🟢 Pawn structure
        score += evaluatePassedPawns(board);
        score += evaluateDoubledPawns(board);
        score += evaluateIsolatedPawns(board);

        // 🟢 Bishop pair
        score += evaluateBishopPair(board);

        // 🟢 Rook activity (files)
        score += evaluateRookFiles(board);

        // 🟢 Center control
        score += CenterControl(board);

        return score;
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

    private BoardState boardAfterMove(Square[][] originalBoard, Move move) {
        BoardState TheNewState = new BoardState();
        Square[][] newBoard = new Square[8][8];
        King White_King = null;
        King Black_King = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                newBoard[i][j] = new Square(null, originalBoard[i][j].c, i, j);

                if (!originalBoard[i][j].isEmpty()) {
                    Piece ClonedPiece = originalBoard[i][j].getPiece().clone();
                    newBoard[i][j].setPiece(ClonedPiece);
                    if (ClonedPiece instanceof King) {
                        if (ClonedPiece.color.equals("White")) {
                            White_King = (King) ClonedPiece;
                        } else {
                            Black_King = (King) ClonedPiece;
                        }
                    }
                }
            }

        }

        Piece pieceToMove = newBoard[move.startRow][move.startCol].getPiece();
        newBoard[move.startRow][move.startCol].removePiece();
        newBoard[move.endRow][move.endCol].setPiece(pieceToMove);

        TheNewState.board = newBoard;
        TheNewState.blackKing = Black_King;
        TheNewState.whiteKing = White_King;

        return TheNewState;

    }

    @Override
    public Move findBestMove(Square[][] board, String aiColor) {
        boolean maximizing = true;
        int bestVal = -INF;
        Move bestMove = null;

        for (Move move : legalMoves(board, maximizing)) {
            int eval = minimax(boardAfterMove(board, move).board, 3, -INF, INF, !maximizing);
            if (eval > bestVal) {
                bestVal = eval;
                bestMove = move;
            }
        }

        return bestMove;
    }

}
