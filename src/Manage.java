public class Manage {
    private final App controller;
    Player player1;
    Player player2;
    static Player player;

    public Manage(App controller) {
        this.controller = controller;
        player1 = new Player(Board.player_name1.getText(), "White");
        player2 = new Player(Board.player_name2.getText(), "Black");
        player1.startTurn();
        player = player1;

    }

    public String get_name() {
        return player.get_name();
    }

    public String get_color() {
        return player.get_color();
    }

    public void change_player() {
        if (player == player1) {
            player = player2;
            player1.stopTurn();
            player2.startTurn();
        } else {
            player = player1;
            player2.stopTurn();
            player1.startTurn();
        }
    }
}
