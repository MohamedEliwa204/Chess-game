import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Square extends JLabel implements ActionListener {
    // what is contain, position, is_empty, set_piece, get_piece, remove_piece.

    Piece piece;
    JButton button;
    int row;
    int col;
    Color c;
    public static Square lastDropTarget = null;
    Board parentBoard;

    public Square(Color c, int row, int col) {

        button = new JButton();
        this.setLayout(new BorderLayout());
        this.row = row;
        this.col = col;
        // to make the button with no animation
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(this);
        // here i am making the button to say that he will have what to be dragged
        button.setTransferHandler(new PieceTransferHandler(this));
        // here this code meaning that when you the button pulled make the drag action
        button.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                JButton b = (JButton) e.getSource();
                TransferHandler handler = b.getTransferHandler();
                handler.exportAsDrag(b, e, TransferHandler.MOVE);
            }
        });
        // here the swing will allow anything that is pulled (piece) to be put in the
        // button
        button.setDropTarget(new DropTarget());

        button.setDropTarget(new DropTarget(button, DnDConstants.ACTION_MOVE, new java.awt.dnd.DropTargetAdapter() {
            @Override
            public void drop(java.awt.dnd.DropTargetDropEvent dtde) {
                lastDropTarget = Square.this;
                dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                dtde.dropComplete(true);
            }
        }, true, null));

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
        // throw new UnsupportedOperationException("Unimplemented method
        // 'actionPerformed'");
    }
}
