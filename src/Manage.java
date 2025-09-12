public class Manage {
    private final App controller;
    static Player player1;
    static Player player2;
    static Player player;
    String n1;
    String n2;
    int t;

    public Manage(App controller, String player1Name, String player2Name, int t) {
        this.controller = controller;
        this.n1 = player1Name;
        this.n2 = player2Name;
        this.t = t;
        player1 = new Player(player1Name, "White", t);
        player2 = new Player(player2Name, "Black", t);
        new Thread(player1).start();
        new Thread(player2).start();
        player1.startTurn();
        player = player1;

    }

    public void reset() {
        // player.shutdown();
        // player1.shutdown();
        // player2.shutdown();
        player1 = new Player(n1, "White", t);
        player2 = new Player(n2, "Black", t);
        new Thread(player1).start();
        new Thread(player2).start();
        player1.startTurn();
        player = player1;
    }

    public Player get_player1() {
        return player1;
    }

    public Player get_player2() {
        return player2;
    }

    public String get_name() {
        return player.get_name();
    }

    public String get_color() {
        return player.get_color();
    }

    static public void change_player() {
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
