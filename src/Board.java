import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Board extends JPanel {
    // initialize board
    // move piece
    // display board
    private static final int imageSize = 60;
    JFrame frame;
    public Square[][] board = new Square[8][8];
    JLabel killed1 = new JLabel();
    JLabel killed2 = new JLabel();
    JLabel player_name1 = new JLabel();
    JLabel player_name2 = new JLabel();
    JLabel player_time1 = new JLabel();
    JLabel player_time2 = new JLabel();
    // storing the row images.
    ImageIcon rowPawnB = new ImageIcon(getClass().getResource("/pieces/pawn_B.png"));
    ImageIcon rowPawnW = new ImageIcon(getClass().getResource("/pieces/pawn_W.png"));
    ImageIcon rowRookB = new ImageIcon(getClass().getResource("/pieces/rook_B.png"));
    ImageIcon rowRookW = new ImageIcon(getClass().getResource("/pieces/rook_W.png"));
    ImageIcon rowKnightB = new ImageIcon(getClass().getResource("/pieces/Knight_B.png"));
    ImageIcon rowKnightW = new ImageIcon(getClass().getResource("/pieces/Knight_W.png"));
    ImageIcon rowBishopB = new ImageIcon(getClass().getResource("/pieces/bishop_B.png"));
    ImageIcon rowBishopW = new ImageIcon(getClass().getResource("/pieces/bishop_W.png"));
    ImageIcon rowQueenB = new ImageIcon(getClass().getResource("/pieces/queen_B.png"));
    ImageIcon rowQueenW = new ImageIcon(getClass().getResource("/pieces/queen_W.png"));
    ImageIcon rowKingB = new ImageIcon(getClass().getResource("/pieces/king_B.png"));
    ImageIcon rowKingW = new ImageIcon(getClass().getResource("/pieces/king_W.png"));

    public Board() {
        // the indexing of the rows and columns of the grid.
        this.setLayout(new GridLayout(8, 8));
        JPanel leftPanel = new JPanel(new GridLayout(8, 1));
        JPanel bottomPanel = new JPanel(new GridLayout(1, 8));
        for (int i = 1; i <= 8; i++) {
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            leftPanel.add(label);
        }
        for (char i = 'a'; i <= 'h'; i++) {
            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            bottomPanel.add(label);
        }
        leftPanel.setBackground(new Color(146, 160, 127));
        bottomPanel.setBackground(new Color(146, 160, 127));
        JPanel player1 = new JPanel(new GridBagLayout());
        JPanel player2 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.2;
        player1.add(player_name1, gbc);
        player2.add(player_name2, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.2;
        player1.add(player_time1, gbc);
        player2.add(player_time2, gbc);
        gbc.gridy = 2;
        gbc.weighty = 0.6;

        player1.add(killed1, gbc);
        player2.add(killed2, gbc);
        // putting the squares in the board and put pieces in the square.
        for (int i = 0; i < 8; i++) {
            char temp = 'a';
            for (int j = 0; j < 8; j++) {

                if ((i + j) % 2 == 0) {
                    board[i][j] = new Square(new Color(216, 198, 160), (char) ((i + 1) + '0'), temp);
                } else {
                    board[i][j] = new Square(new Color(126, 110, 99), (char) ((i + 1) + '0'), temp);
                }
                if (i == 1) {
                    board[i][j].setPiece(new Pawn("White", i + 1, j + 1,
                            scaleImage(rowPawnW, imageSize, imageSize),
                            getVerifyInputWhenFocusTarget()));
                }
                if (i == 6) {
                    board[i][j].setPiece(new Pawn("Black", i + 1, j + 1,
                            scaleImage(rowPawnB, imageSize, imageSize),
                            getVerifyInputWhenFocusTarget()));
                }
                if (i == 0 && temp == 'd') {
                    board[i][j]
                            .setPiece(new Queen("White", i + 1, j + 1, scaleImage(rowQueenW, imageSize, imageSize)));
                }
                if (i == 7 && temp == 'd') {
                    board[i][j].setPiece(new Queen("Black", i + 1, j + 1, scaleImage(rowQueenB, imageSize, imageSize)));
                }
                if (i == 0 && (temp == 'a' || temp == 'h')) {
                    board[i][j].setPiece(new Rook("White", i + 1, j + 1, scaleImage(rowRookW, imageSize, imageSize)));
                }
                if (i == 7 && (temp == 'a' || temp == 'h')) {
                    board[i][j].setPiece(new Rook("Black", i + 1, j + 1, scaleImage(rowRookB, imageSize, imageSize)));
                }
                if (i == 0 && (temp == 'b' || temp == 'g')) {
                    board[i][j]
                            .setPiece(new Knight("White", i + 1, j + 1, scaleImage(rowKnightW, imageSize, imageSize)));

                }
                if (i == 7 && (temp == 'b' || temp == 'g')) {
                    board[i][j]
                            .setPiece(new Knight("Black", i + 1, j + 1, scaleImage(rowKnightB, imageSize, imageSize)));

                }
                if (i == 0 && (temp == 'c' || temp == 'f')) {
                    board[i][j]
                            .setPiece(new Bishop("White", i + 1, j + 1, scaleImage(rowBishopW, imageSize, imageSize)));

                }
                if (i == 7 && (temp == 'c' || temp == 'f')) {
                    board[i][j]
                            .setPiece(new Bishop("Black", i + 1, j + 1, scaleImage(rowBishopB, imageSize, imageSize)));

                }
                if (i == 0 && (temp == 'e')) {
                    board[i][j].setPiece(new King("White", i + 1, j + 1, scaleImage(rowKingW, imageSize, imageSize),
                            getFocusTraversalKeysEnabled()));
                }
                if (i == 7 && (temp == 'e')) {
                    board[i][j].setPiece(new King("Black", i + 1, j + 1, scaleImage(rowKingB, imageSize, imageSize),
                            getFocusTraversalKeysEnabled()));
                }
                this.add(board[i][j]);
                temp += 1;
            }

        }
        JPanel grid = new JPanel();
        grid.setLayout(new BorderLayout(0, 0));
        grid.add(bottomPanel, BorderLayout.SOUTH);
        grid.add(leftPanel, BorderLayout.WEST);
        grid.add(this, BorderLayout.CENTER);
        frame = new JFrame();
        frame.setLayout(new BorderLayout(0, 0));
        player1.setPreferredSize(new Dimension(150, 0));
        player2.setPreferredSize(new Dimension(150, 0));
        ImageIcon icon = new ImageIcon("C:\\Users\\Mohamed\\Downloads\\king-1716907_640.jpg");
        frame.setIconImage(icon.getImage());
        frame.setTitle("Hello in our chess game!");
        frame.setResizable(true);
        frame.setSize(650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(player2, BorderLayout.EAST);
        frame.add(player1, BorderLayout.WEST);
        frame.add(grid, BorderLayout.CENTER);
    }

    private ImageIcon scaleImage(ImageIcon icon, int w, int h) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }

}
