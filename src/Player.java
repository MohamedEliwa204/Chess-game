public class Player implements Runnable {
    private String name;
    private String color;
    private int sec = 0;
    private int min = 0;
    private boolean turn = false;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
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
}

