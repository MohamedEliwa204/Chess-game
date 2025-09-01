import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.*;

import javax.swing.*;

public class Square extends JLabel implements ActionListener {
    // what is contain, position, is_empty, set_piece, get_piece, remove_piece.

    Piece piece;
    JButton button;
    int row;
    int col;
    ImageIcon temp_icon;
    Color c;
    public static Square lastDropTarget = null;
    public static Piece draggedPiece = null;
    Board parentBoard;

    public Square(Color c, int row, int col) {
        ClickListener clickListener = new ClickListener();
        DragListener dragListerner = new DragListener();
        ReleaseListener releaseListener = new ReleaseListener();
        button = new JButton();
        this.setLayout(new BorderLayout());
        this.row = row;
        this.col = col;
        // to make the button with no animation
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(this);

        this.add(button, BorderLayout.CENTER);
        this.setBackground(c);
        this.setOpaque(true);
        this.c = c;
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListerner);
        this.addMouseListener(releaseListener);
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

    public void setParentBoard(Board board) {
        this.parentBoard = board;
    }

    public Board getParentBoard() {
        return this.parentBoard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            // will be implemented
            System.out.printf("hello in my square! (%c,%c)%n", row, col);
        }
    }

    private class ClickListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            Square currentSquare = (Square) e.getSource();
            if (!currentSquare.isEmpty()) {
                lastDropTarget = currentSquare;

                draggedPiece = currentSquare.getPiece();
                temp_icon = draggedPiece.icon;
                currentSquare.removePiece();
            }
        }
    }

    private class DragListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (temp_icon != null) {
                Graphics g = getParent().getGraphics();
                Point p = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), getParent());
                g.drawImage(temp_icon.getImage(), p.x, p.y, null);
            }
        }
    }

    private class ReleaseListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (lastDropTarget != null && draggedPiece != null) {
                Square targetSquare = (Square) e.getSource();

                if (draggedPiece.isValidMove(targetSquare.getRow(), targetSquare.getCol(),
                        targetSquare.getParentBoard().board)) {
                    if (!targetSquare.isEmpty()) {
                        targetSquare.getParentBoard().setKilledPiece(targetSquare.getPiece());
                    }
                    targetSquare.setPiece(draggedPiece);
                } else {

                    lastDropTarget.setPiece(draggedPiece);
                }

                // reset
                draggedPiece = null;
                temp_icon = null;
                lastDropTarget = null;
            }
        }
    }

}
