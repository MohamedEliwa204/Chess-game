public class WeightedMove {
    public final int weight;
    public final Move move;

    public WeightedMove(int w, Move m) {
        this.weight = w;
        this.move = m;
    }

    public int getWeight() {
        return this.weight;
    }

    public Move getMove() {
        return this.move;
    }
}
