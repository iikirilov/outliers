package reader;

public class Context {
    private int lookback = 10;
    private long count;
    private float avg;

    public Context(int lookback, long count, float avg) {
        this.lookback = lookback;
        this.count = count;
        this.avg = avg;
    }
}
