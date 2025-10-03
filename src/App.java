import java.awt.BorderLayout;
import java.awt.CardLayout;

import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class App implements ActionListener {
    private final CardLayout cardLayout;
    JFrame mainFrame;
    JPanel cardPanel;
    MainMenu mainMenu;
    SettingMenu settingMenu;
    SettingMenu2 settingMenu2;
    Board board;
    Manage manage;
    int min;
    EasyEngine easy;
    MediumEngine medium;
    HardEngine hard;
    String level;

    GameManage gamemanage;

    public App() {
                // Create the main application window with a title identifying the chess game and its authors.
        mainFrame = new JFrame("Elshewy&Gabr Chess Game!");
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/king.jpg"));
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH);
        mainFrame.setLayout(new BorderLayout(0, 0));
        mainFrame.setIconImage(resized);
        mainFrame.setResizable(true);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainMenu = new MainMenu(this);
        settingMenu = new SettingMenu(this);
        settingMenu2 = new SettingMenu2(this);
        gamemanage = new GameManage(this);
        board = new Board(this);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(mainMenu, "Main menu");
        cardPanel.add(settingMenu, "Setting Menu");
        cardPanel.add(settingMenu2, "Setting Menu2");
        cardPanel.add(board, "Play board");

        mainFrame.add(cardPanel);
        mainFrame.setVisible(true);
    }

    public void MakeMove(Move move) {
        Piece piece = move.movedPiece;
        piece.row = move.endRow;
        piece.col = move.endCol;
        board.board[move.startRow][move.startCol].removePiece();
        board.board[move.endRow][move.endCol].setPiece(piece);
        if (move.capturedPiece != null) {
            board.setKilledPiece(move.capturedPiece);
        }
    }

    public void startGame(String n1, String n2, int t) {
        this.manage = new Manage(this, n1, n2, t);
        board.setupGame(this.manage);
        cardLayout.show(cardPanel, "Play board");

    }

    public void showSetting() {

        cardLayout.show(cardPanel, "Setting Menu");
    }

    public void showSetting2() {
        cardLayout.show(cardPanel, "Setting Menu2");
    }

    public void backToMain() {
        cardLayout.show(cardPanel, "Main menu");
    }

    public void restart() {
        manage.reset();
        cardPanel.remove(board);
        board = new Board(this);
        board.setupGame(this.manage);
        cardPanel.add(board, "Play board");
        cardLayout.show(cardPanel, "Play board");
    }

    public static void main(String[] args) throws Exception {

        new App();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
