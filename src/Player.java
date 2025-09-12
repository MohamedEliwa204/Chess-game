import java.util.ArrayList;

public class Player implements Runnable {
    private String name;
    private String color;
    private int sec;
    private int min;
    private volatile boolean turn = false;
    private volatile boolean isRunning = true;
    private ArrayList<Piece> killedPieces;

    public Player(String name, String color, int t) {
        this.name = name;
        this.min = t;
        this.color = color;
        this.killedPieces = new ArrayList<>();
    }

    @Override
    public void run() {
        // 3. The outer loop now checks 'isRunning' to allow for a clean exit.
        while (isRunning) {
            try {
                synchronized (this) {
                    // This loop efficiently waits while it's not our turn.
                    while (!turn && isRunning) {
                        // wait() pauses the thread here, using 0% CPU.
                        this.wait();
                    }
                }

                // If the thread was woken up just to be shut down, exit the loop.
                if (!isRunning) {
                    break;
                }

                // If we get here, it must be our turn. Do the timer work.
                if (turn) {
                    // Sleep for one second before decrementing.
                    Thread.sleep(1000);

                    // Timer logic now counts DOWN.
                    sec--;
                    if (sec < 0) {
                        min--;
                        sec = 59;
                    }

                    if (min < 0) {
                        System.out.println(name + " ran out of time!");
                        stopTurn(); // Stop your own turn if time runs out.
                        // You would also notify the main game controller here.
                    }
                }

            } catch (InterruptedException e) {
                // If the thread is interrupted, treat it as a signal to shut down.
                this.isRunning = false;
            }
        }
        System.out.println("Timer thread for " + name + " has finished.");
    }

    public String get_name() {
        return this.name;
    }

    public String get_color() {
        return this.color;
    }

    public void startTurn() {
        synchronized (this) {
            this.turn = true;

            this.notifyAll();
        }
    }

    public void stopTurn() {
        turn = false;
    }

    public void shutdown() {
        synchronized (this) {
            this.isRunning = false;
            this.notifyAll();
        }
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

    public void setKilledPiece(Piece killedPiece) {
        killedPieces.add(killedPiece);
    }

    public ArrayList<Piece> getKilledPieces() {
        return killedPieces;
    }

    public String getTime() { // i think this method will be easy when making the time panel and the player
                              // finished playing
        return String.format("%02d:%02d", Math.max(min, 0), Math.max(0, sec));
    }
}
