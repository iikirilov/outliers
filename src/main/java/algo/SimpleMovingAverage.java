package algo;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Moving Average Algorithm implementation using Apache Math
 */
public class SimpleMovingAverage implements ContextAwareAlogorithm {

    public static int DEFAULT_WINDOW = 10;

    private final DescriptiveStatistics ds;

    private int iteration;
    private double ma;

    public SimpleMovingAverage() {
        this(DEFAULT_WINDOW);
    }

    public SimpleMovingAverage(final int defaultWindow) {
        this.ds = new DescriptiveStatistics(defaultWindow);
        this.iteration = 0;
    }

    @Override
    public int getWindow() {
        return ds.getWindowSize();
    }

    @Override
    public void setWindow(int window) {
        ds.setWindowSize(window);
    }

    @Override
    public int getIteration() {
        return iteration;
    }

    @Override
    public void nextInput(final double newPrice) {
        ds.addValue(newPrice);
        ma = ds.getMean();
        ++iteration;
    }

    @Override
    public double output() {
        return ma;
    }

    @Override
    public void undo() {
        ds.removeMostRecentValue();
        ma = ds.getMean();
    }
}
