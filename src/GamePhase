public class GamePhase {

    public static boolean isEndgame(Square[][] board) {
        int queens = 0, rooks = 0, bishops = 0, knights = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board[i][j].getPiece();
                if (p == null) continue;

                if (p instanceof Queen) queens++;
                else if (p instanceof Rook) rooks++;
                else if (p instanceof Bishop) bishops++;
                else if (p instanceof Knight) knights++;
            }
        }

        int phase = 4 * queens + 2 * rooks + bishops + knights;

        return phase <= 6;
    }
}
