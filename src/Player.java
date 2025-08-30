import java.util.ArrayList;

public class Player implements Runnable {
    private String name;
    private String color;
    private int sec = 0;
    private int min = 0;
    private boolean turn = false;
    private ArrayList<Piece> killedPieces;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        this.killedPieces = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            if (turn) {
                sec++;
                if (sec == 60) {
                    min++;
                    sec = 0;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(name + " interrupted!");
                    break;
                }
            }
        }
    }

    public void startTurn() {
        turn = true;
    }

    public void stopTurn() {
        turn = false;
    }

    public boolean isTurn() {
        return turn;
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }

    public void reset() {
        sec = 0;
        min = 0;
    }

    public void setKilledPiece(Piece killedPiece){
        killedPieces.add(killedPiece);
    }

    public ArrayList<Piece> getKilledPieces() {
        return killedPieces;
    }

    public String getTime() { // i think this method will be easy when making the time panel and the player finished playing
        return String.format("%02d:%02d", min, sec);
    }
}
