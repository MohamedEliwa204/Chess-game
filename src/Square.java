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
        if (this.getPiece() != null && this.getPiece().color.equals(Player_color)) {
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
            System.out.println("--- MOUSE RELEASED ---"); // Start of event

            // 1. Find the target square
            Point mousePoint = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), parentBoard);
            Component releasedOn = parentBoard.getComponentAt(mousePoint);

            Square targetSquare = null;
            if (releasedOn instanceof Square) {
                targetSquare = (Square) releasedOn;
            } else if (releasedOn != null && releasedOn.getParent() instanceof Square) {
                targetSquare = (Square) releasedOn.getParent();
            }

            // 2. DEBUG: Check if the target square was found
            if (targetSquare != null) {
                System.out.println("DEBUG: Mouse released over a square at [" + targetSquare.getRow() + ", "
                        + targetSquare.getCol() + "]");

                // 3. DEBUG: Check if the move is considered valid by your game logic
                boolean isMoveValid = Dragged_Piece.isValidMove(targetSquare.row, targetSquare.col, parentBoard.board);
                System.out.println("DEBUG: Checking move for " + Dragged_Piece.getClass().getSimpleName() + " to ["
                        + targetSquare.row + ", " + targetSquare.col + "]. Result: " + isMoveValid);

                if (isMoveValid) {
                    System.out.println("SUCCESS: Move is valid. Placing piece.");
                    if (!targetSquare.isEmpty()) {
                        parentBoard.setKilledPiece(targetSquare.getPiece());
                    }
                    targetSquare.setPiece(Dragged_Piece);
                    parentBoard.clear_add_color();

                } else {
                    System.out.println("FAILURE: Move is NOT valid. Returning piece.");
                    parentBoard.board[Dragged_row][Dragged_col].setPiece(Dragged_Piece);
                    parentBoard.clear_add_color();
                }
            } else {
                System.out.println("FAILURE: Mouse released, but targetSquare is NULL. Returning piece.");
                parentBoard.board[Dragged_row][Dragged_col].setPiece(Dragged_Piece);
                parentBoard.clear_add_color();
            }

            // 4. Clean up the drag state
            Dragged_Piece = null;
            Dragged_from_square = null;
            System.out.println("--- END OF EVENT ---\n");
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
