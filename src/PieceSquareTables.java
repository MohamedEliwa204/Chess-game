public class PieceSquareTables {

    // 🟢 Pawn PST (Black at bottom)
    private static final int[][] PAWN = {
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 5, 10, 10,-20,-20, 10, 10,  5},
            { 5, -5,-10,  0,  0,-10, -5,  5},
            { 0,  0,  0, 20, 20,  0,  0,  0},
            { 5,  5, 10, 25, 25, 10,  5,  5},
            {10, 10, 20, 30, 30, 20, 10, 10},
            {50, 50, 50, 50, 50, 50, 50, 50},
            { 0,  0,  0,  0,  0,  0,  0,  0}
    };

    // 🟢 Knight PST
    private static final int[][] KNIGHT = {
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50}
    };

    // 🟢 Bishop PST
    private static final int[][] BISHOP = {
            {-20,-10,-10,-10,-10,-10,-10,-20},
            {-10,  5,  0,  0,  0,  0,  5,-10},
            {-10, 10, 10, 10, 10, 10, 10,-10},
            {-10,  0, 10, 10, 10, 10,  0,-10},
            {-10,  5,  5, 10, 10,  5,  5,-10},
            {-10,  0,  5, 10, 10,  5,  0,-10},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-20,-10,-10,-10,-10,-10,-10,-20}
    };

    // 🟢 Rook PST
    private static final int[][] ROOK = {
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 5, 10, 10, 10, 10, 10, 10,  5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            { 0,  0,  0,  5,  5,  0,  0,  0}
    };

    // 🟢 Queen PST
    private static final int[][] QUEEN = {
            {-20,-10,-10, -5, -5,-10,-10,-20},
            {-10,  0,  5,  0,  0,  0,  0,-10},
            {-10,  5,  5,  5,  5,  5,  0,-10},
            {  0,  0,  5,  5,  5,  5,  0, -5},
            { -5,  0,  5,  5,  5,  5,  0, -5},
            {-10,  0,  5,  5,  5,  5,  0,-10},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-20,-10,-10, -5, -5,-10,-10,-20}
    };

    // 🟢 King PST (Opening/Middlegame)
    private static final int[][] KING_MID = {
            { 20, 30, 10,  0,  0, 10, 30, 20},
            { 20, 20,  0,  0,  0,  0, 20, 20},
            {-10,-20,-20,-20,-20,-20,-20,-10},
            {-20,-30,-30,-40,-40,-30,-30,-20},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30}
    };

    // 🟢 King PST (Endgame)
    private static final int[][] KING_END = {
            {-50,-30,-30,-30,-30,-30,-30,-50},
            {-30,-30,  0,  0,  0,  0,-30,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-20,-10,  0,  0,-10,-20,-30},
            {-50,-40,-30,-20,-20,-30,-40,-50}
    };

    public static int getPieceSquareValue(Piece piece, int row, int col, boolean isBlack, boolean endgame) {
        int effectiveRow = isBlack ? row : 7 - row; // flip for White

        if (piece instanceof Pawn) {
            return PAWN[effectiveRow][col];
        } else if (piece instanceof Knight) {
            return KNIGHT[effectiveRow][col];
        } else if (piece instanceof Bishop) {
            return BISHOP[effectiveRow][col];
        } else if (piece instanceof Rook) {
            return ROOK[effectiveRow][col];
        } else if (piece instanceof Queen) {
            return QUEEN[effectiveRow][col];
        } else if (piece instanceof King) {
            return endgame ? KING_END[effectiveRow][col] : KING_MID[effectiveRow][col];
        }
        return 0;
    }
}
