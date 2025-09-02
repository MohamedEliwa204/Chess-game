import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.*;

import javax.swing.*;

public class Square extends JLabel implements MouseMotionListener, MouseListener {
    // what is contain, position, is_empty, set_piece, get_piece, remove_piece.
    static int Dragged_row;
    static int Dragged_col;
    static Piece Dragged_Piece;
    static Square Dragged_from_square;
    static String Player_color;
    static Board parentBoard;
    Piece piece;
    JButton button;
    int row;
    int col;
    Color c;

    public Square(Color c, int row, int col) {

        button = new JButton();
        this.setLayout(new BorderLayout());
        this.row = row;
        this.col = col;
        // to make the button with no animation
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.addMouseListener(this);
        button.addMouseMotionListener(this);
        // button.addActionListener(this);
        // this.addMouseListener(this);
        // this.addMouseMotionListener(this);

        this.add(button, BorderLayout.CENTER);
        this.setBackground(c);
        this.setOpaque(true);
        this.c = c;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece spiece) {
        piece = spiece;
        if (spiece != null) {
            this.button.setIcon(spiece.icon);
        } else {
            this.button.setIcon(null);
        }
    }

    public void removePiece() {
        piece = null;
        this.button.setIcon(null);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == this.button && this.getPiece() != null) {
            Dragged_Piece = this.getPiece();
            Dragged_row = this.row;
            Dragged_col = this.col;
            Dragged_from_square = this;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (Dragged_Piece.isValidMove(i, j, parentBoard.board)) {
                        parentBoard.board[i][j].setBackground(Color.GREEN);
                        parentBoard.board[i][j].repaint();
                    }
                }
            }
            this.removePiece();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (Dragged_Piece != null) {
            Square targetSquare = null;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    // here i wanna know where is the exactly the mouse pointer is
                    Point p = e.getLocationOnScreen();
                    // converting this point to a square as if the square contains the pointer of
                    // the mouse so this is
                    // the targeted square
                    SwingUtilities.convertPointFromScreen(p, parentBoard.board[i][j]);

                    if (parentBoard.board[i][j].contains(p)) {
                        targetSquare = parentBoard.board[i][j];
                        break;
                    }
                }
                // if i searched and didn't find the pointer of the square on any square so
                // there is no target or the
                // mouse pointer is out of the board
                if (targetSquare != null) {
                    break;
                }
            }
            if (targetSquare != null
                    && Dragged_Piece.isValidMove(targetSquare.row, targetSquare.col, parentBoard.board)) {
                if (targetSquare.isEmpty()) {
                    // here if the square is empty then put the piece and reset the color of the
                    // board again
                    targetSquare.setPiece(Dragged_Piece);
                    parentBoard.clear_add_color();
                } else {
                    // here there is an enemy's piece then we will kill it
                    parentBoard.setKilledPiece(targetSquare.getPiece());
                    targetSquare.setPiece(Dragged_Piece);
                    parentBoard.clear_add_color();
                }
            } else {
                // setting the piece back to it's older place that's because the move isn't
                // valid
                parentBoard.board[Dragged_row][Dragged_col].setPiece(Dragged_Piece);
            }

            Dragged_Piece = null;
            // Dragged_from_square = null;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // @Override
    // public void actionPerformed(ActionEvent e) {
    // // TODO Auto-generated method stub
    // if (e.getSource() == button) {
    // System.out.printf("%d %d", row, col);
    // }
    // }
}
