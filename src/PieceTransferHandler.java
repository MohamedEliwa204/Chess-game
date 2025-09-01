import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class PieceTransferHandler extends TransferHandler {
    Square sourceSquare;

    public PieceTransferHandler(Square sourceSquare) {
        super("icon");
        this.sourceSquare = sourceSquare;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        // وقت السحب: نخفي الـ icon مؤقت
        sourceSquare.button.setIcon(null);
        return new StringSelection("dummy");
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if (action == MOVE) {
            Square targetSquare = Square.lastDropTarget; // هتعمل دالة ترجع المربع اللي اتعمل عليه drop
            Piece piece = sourceSquare.getPiece();
            if (piece != null && targetSquare != null) {
                boolean valid = piece.isValidMove(targetSquare.getRow(), targetSquare.getCol(),
                        sourceSquare.getParentBoard().board);
                if (valid) {
                    // لو فيه قطعة في المربع الجديد → تتحط في killed panel
                    if (!targetSquare.isEmpty()) {
                        sourceSquare.getParentBoard().setKilledPiece(targetSquare.getPiece());
                    }
                    // انقل القطعة
                    targetSquare.setPiece(piece);
                    piece.move(targetSquare.getRow(), targetSquare.getCol());
                    sourceSquare.removePiece();
                } else {
                    // الحركة غلط → رجع الـ icon للمربع الأصلي
                    sourceSquare.button.setIcon(piece.icon);
                }
            }
        }
    }
}
