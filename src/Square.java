
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.*;
import java.util.Stack;

import javax.swing.*;

public class Square extends JLabel implements MouseMotionListener, MouseListener {
    // what is contain, position, is_empty, set_piece, get_piece, remove_piece.
    int Dragged_row;
    static int Dragged_col;
    static Piece Dragged_Piece;
    static Square Dragged_from_square;
    static Board parentBoard;
    private final App controller;
    Manage manage;
    Piece piece;
    JButton button;
    int row;
    int col;
    Color c;

    public Square(App controller, Color c, int row, int col) {
        this.controller = controller;
        // manage = new Manage(null);
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
        // must be a piece of the current player
        if (Manage.player.get_name().equals("Engine")) {
            return;
        }
        if (e.getSource() == this.button && this.getPiece() != null
                && Manage.player.get_color().equals(this.piece.color)) {

            if (GameLogic.isInCheck(Manage.player.get_color(), parentBoard.board)) {
                if (!GameLogic.possiblePieces.contains(this.getPiece())) {
                    // not allowed, ignore this click
                    return;
                }
            }

            Dragged_Piece = this.getPiece();
            Dragged_row = this.row;
            Dragged_col = this.col;
            Dragged_from_square = this;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (Dragged_Piece.isValidMove(i, j, parentBoard.board)) {
                        // simulate move: only show legal moves that don’t leave king in check
                        Piece captured = parentBoard.board[i][j].getPiece();
                        parentBoard.board[Dragged_row][Dragged_col].setPiece(null);
                        parentBoard.board[i][j].setPiece(Dragged_Piece);

                        boolean stillCheck = GameLogic.isInCheck(Dragged_Piece.color, parentBoard.board);

                        parentBoard.board[Dragged_row][Dragged_col].setPiece(Dragged_Piece);
                        parentBoard.board[i][j].setPiece(captured);

                        if (!stillCheck) {
                            parentBoard.board[i][j].setBackground(Color.GREEN);
                            parentBoard.board[i][j].repaint();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        parentBoard.clear_add_color();
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
                Piece capturedPiece = targetSquare.getPiece();

                // 2. Create a single 'Move' object that contains all information about this
                // turn.
                Move newMove = new Move(
                        Dragged_Piece,
                        Dragged_from_square.getRow(),
                        Dragged_from_square.getCol(),
                        targetSquare.getRow(),
                        targetSquare.getCol(),
                        capturedPiece);

                // 3. Push the single Move object onto the stack. This works for both captures
                // and simple moves.
                Board.moveStack.push(newMove);

                // --- Now, update the board visually ---

                // If a piece was captured, add it to the killed pieces panel.
                if (capturedPiece != null) {
                    parentBoard.setKilledPiece(capturedPiece);
                }

                // These actions are the same for both simple moves and captures.
                Dragged_from_square.removePiece();
                targetSquare.setPiece(Dragged_Piece);
                Dragged_Piece.move(targetSquare.row, targetSquare.col);
                // here i want to make the logic of checking the state of the game at all
                String currentColor = Manage.player.get_color();

                String opponentColor = currentColor.equals("White") ? "Black" : "White";
                GameManage.CheckingState(opponentColor);
                Piece promotedPawn = GameManage.isTherePromotion();
                if (promotedPawn != null) {
                    Pawn pawn = (Pawn) promotedPawn;
                    GameLogic.PawnPromote(pawn, Square.parentBoard);
                }
                Manage.change_player();

            } else {
                // setting the piece back to it's older place that's because the move isn't
                // valid
                parentBoard.board[Dragged_row][Dragged_col].setPiece(Dragged_Piece);
            }
            if (Manage.player.get_name().equals("Engine")) {
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Move move;
                        if (controller.level.equals("Easy")) {
                            move = controller.easy.findBestMove(controller.board.board, "Black");
                        } else if (controller.level.equals("Medium")) {
                            move = controller.medium.findBestMove(controller.board.board, "Black");
                        } else {
                            move = controller.hard.findBestMove(controller.board.board, "Black");
                        }
                        Board.moveStack.push(move);
                        controller.MakeMove(move);
                        Manage.change_player();
                    }
                });
                timer.setRepeats(false); // only run once
                timer.start();
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

}
