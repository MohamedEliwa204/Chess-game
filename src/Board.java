import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
    private static final int IMAGE_SIZE = 60;
    private static final int CAPTURED_IMAGE_SIZE = 25;

    public Square[][] board = new Square[8][8];
    JPanel killed1 = new JPanel(new FlowLayout());
    JPanel killed2 = new JPanel(new FlowLayout());
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
        JPanel tempPanel = new JPanel(new GridLayout(8, 8));
        this.setLayout(new BorderLayout());
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
        player_name1.setOpaque(true);
        player_name2.setOpaque(true);
        player_name1.setBackground(new Color(26, 65, 70));
        player_name1.setForeground(new Color(232, 224, 200));
        player_name2.setForeground(new Color(232, 224, 200));
        player_name2.setBackground(new Color(26, 65, 70));
        player_name1.setFont(new Font("Georgia", Font.BOLD, 18));
        player_name2.setFont(new Font("Georgia", Font.BOLD, 18));
        player_time1.setOpaque(true);
        player_time2.setOpaque(true);
        player_time1.setBackground(new Color(39, 114, 114));
        player_time2.setBackground(new Color(39, 114, 114));
        player_time1.setForeground(new Color(232, 224, 200));
        player_time2.setForeground(new Color(232, 224, 200));
        player_time1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        player_time2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        killed1.setOpaque(true);
        killed2.setOpaque(true);
        killed1.setBackground(new Color(232, 224, 200));
        killed2.setBackground(new Color(232, 224, 200));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
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
                            scaleImage(rowPawnW, IMAGE_SIZE, IMAGE_SIZE),
                            getVerifyInputWhenFocusTarget()));
                }
                if (i == 6) {
                    board[i][j].setPiece(new Pawn("Black", i + 1, j + 1,
                            scaleImage(rowPawnB, IMAGE_SIZE, IMAGE_SIZE),
                            getVerifyInputWhenFocusTarget()));
                }
                if (i == 0 && temp == 'd') {
                    board[i][j]
                            .setPiece(new Queen("White", i + 1, j + 1, scaleImage(rowQueenW, IMAGE_SIZE, IMAGE_SIZE)));
                }
                if (i == 7 && temp == 'd') {
                    board[i][j]
                            .setPiece(new Queen("Black", i + 1, j + 1, scaleImage(rowQueenB, IMAGE_SIZE, IMAGE_SIZE)));
                }
                if (i == 0 && (temp == 'a' || temp == 'h')) {
                    board[i][j].setPiece(new Rook("White", i + 1, j + 1, scaleImage(rowRookW, IMAGE_SIZE, IMAGE_SIZE)));
                }
                if (i == 7 && (temp == 'a' || temp == 'h')) {
                    board[i][j].setPiece(new Rook("Black", i + 1, j + 1, scaleImage(rowRookB, IMAGE_SIZE, IMAGE_SIZE)));
                }
                if (i == 0 && (temp == 'b' || temp == 'g')) {
                    board[i][j]
                            .setPiece(
                                    new Knight("White", i + 1, j + 1, scaleImage(rowKnightW, IMAGE_SIZE, IMAGE_SIZE)));

                }
                if (i == 7 && (temp == 'b' || temp == 'g')) {
                    board[i][j]
                            .setPiece(
                                    new Knight("Black", i + 1, j + 1, scaleImage(rowKnightB, IMAGE_SIZE, IMAGE_SIZE)));

                }
                if (i == 0 && (temp == 'c' || temp == 'f')) {
                    board[i][j]
                            .setPiece(
                                    new Bishop("White", i + 1, j + 1, scaleImage(rowBishopW, IMAGE_SIZE, IMAGE_SIZE)));

                }
                if (i == 7 && (temp == 'c' || temp == 'f')) {
                    board[i][j]
                            .setPiece(
                                    new Bishop("Black", i + 1, j + 1, scaleImage(rowBishopB, IMAGE_SIZE, IMAGE_SIZE)));

                }
                if (i == 0 && (temp == 'e')) {
                    board[i][j].setPiece(new King("White", i + 1, j + 1, scaleImage(rowKingW, IMAGE_SIZE, IMAGE_SIZE),
                            getFocusTraversalKeysEnabled()));
                }
                if (i == 7 && (temp == 'e')) {
                    board[i][j].setPiece(new King("Black", i + 1, j + 1, scaleImage(rowKingB, IMAGE_SIZE, IMAGE_SIZE),
                            getFocusTraversalKeysEnabled()));
                }
                tempPanel.add(board[i][j]);
                temp += 1;
            }

        }

        JPanel grid = new JPanel();
        grid.setLayout(new BorderLayout(0, 0));
        grid.add(bottomPanel, BorderLayout.SOUTH);
        grid.add(leftPanel, BorderLayout.WEST);
        grid.add(tempPanel, BorderLayout.CENTER);

        player1.setPreferredSize(new Dimension(150, 0));
        player2.setPreferredSize(new Dimension(150, 0));
        // ImageIcon icon = new
        // ImageIcon("C:\\Users\\Mohamed\\Downloads\\king-1716907_640.jpg");
        // frame.setIconImage(icon.getImage());
        // frame.setTitle("Hello in our chess game!");
        // frame.setResizable(true);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setVisible(true);
        // frame.add(player2, BorderLayout.EAST);
        // frame.add(player1, BorderLayout.WEST);
        // frame.add(grid, BorderLayout.CENTER);
        this.add(player2, BorderLayout.EAST);
        this.add(player1, BorderLayout.WEST);
        this.add(grid, BorderLayout.CENTER);
    }

    public void clear_add_color() {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j < 8; j++) {
                Color originalColor = ((i + j) % 2 == 0) ? new Color(216, 198, 160) : new Color(126, 110, 99);
                board[i][j].setBackground(originalColor);
                board[i][j].repaint();
            }
        }
    }

    private ImageIcon scaleImage(ImageIcon icon, int w, int h) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }

    public void setKilledPiece(Piece killed) {
        ImageIcon icon = killed.getIcon();
        ImageIcon smallIcon = scaleImage(icon, CAPTURED_IMAGE_SIZE, CAPTURED_IMAGE_SIZE);
        JLabel temp = new JLabel();
        temp.setIcon(smallIcon);

        if (killed.getColor().equals("White")) {
            killed1.add(temp);
            // refresh panel to update.
            killed1.revalidate();
            killed1.repaint();
        } else {
            killed2.add(temp);
            // refresh panel to update.
            killed2.revalidate();
            killed2.repaint();
        }
    }

    // public void restart() {
    // new Board();
    // setupGame(player_name1.getText(), player_name2.getText(), min);
    // }

    public void setupGame(String name1, String name2, int min) {

        this.player_name1.setText(name1);
        this.player_name2.setText(name2);
        this.player_time1.setText(String.format("%02d:%02d", min, 0));
        this.player_time2.setText(String.format("%02d:%02d", min, 0));
        this.player_name1.setHorizontalAlignment(SwingConstants.CENTER);
        this.player_name2.setHorizontalAlignment(SwingConstants.CENTER);
        this.player_time1.setHorizontalAlignment(SwingConstants.CENTER);
        this.player_time2.setHorizontalAlignment(SwingConstants.CENTER);
        this.player_name1.setFont(new Font("Serif", Font.ITALIC, 24));
        this.player_name2.setFont(new Font("Serif", Font.ITALIC, 24));
        this.player_time1.setFont(new Font("Monospaced", Font.BOLD, 40));
        this.player_time2.setFont(new Font("Monospaced", Font.BOLD, 40));
        this.killed1.removeAll();
        this.killed2.removeAll();
        this.killed1.revalidate();
        this.killed1.repaint();
        this.killed2.revalidate();
        this.killed2.repaint();
    }
}
