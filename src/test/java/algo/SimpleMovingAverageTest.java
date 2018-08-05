package algo;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SimpleMovingAverageTest {

    private ContextAwareAlogorithm sma;

    @Before
    public void setUp() {
        sma = new SimpleMovingAverage();
    }

    @Test
    public void testGetWindow() {
        assertThat(sma.getWindow(), is(10));
    }

    @Test
    public void testSetWindow() {
        sma.setWindow(11);
        assertThat(sma.getWindow(), is(11));
    }

    @Test
    public void testGetIteration() {
        assertThat(sma.getIteration(), is(0));
        sma.nextInput(0);
        assertThat(sma.getIteration(), is(1));
    }

    @Test
    public void testOutput() {
        sma.nextInput(5d);
        assertThat(sma.output(), is(5d));
        sma.nextInput(6d);
        assertThat(sma.output(), is((5d + 6d) / 2d));
        sma.nextInput(6d);
        assertThat(sma.output(), is((5d + 6d + 6d) / 3d));
        sma.nextInput(7d);
        sma.nextInput(5d);
        sma.nextInput(4.5d);
        sma.nextInput(10.1d);
        assertThat(sma.output(), is((5d + 6d + 6d + 7d + 5d + 4.5d + 10.1d) / 7d));
        sma.nextInput(7d);
        sma.nextInput(5d);
        sma.nextInput(4.5d);
        sma.nextInput(2.2d);
        assertThat(sma.output(), is((6.0d + 6.0d + 7.0d + 5.0d + 4.5d + 10.1d + 7.0d + 5.0d + 4.5d + 2.2d) / 10d));
    }

    @Test
    public void testUndo() {
        sma.nextInput(5d);
        assertThat(sma.output(), is(5d));
        sma.nextInput(6d);
        assertThat(sma.output(), is((5d + 6d) / 2d));
        sma.undo();
        assertThat(sma.output(), is(5d));

    }

}
