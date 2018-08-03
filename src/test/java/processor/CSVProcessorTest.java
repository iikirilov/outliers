package processor;

import algo.SimpleMovingAverage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class CSVProcessorTest {

    private File file;
    private CSVProcessor<SimpleMovingAverage> processor;

    @Before
    public void setUpContext() {
        file = new File(getClass().getClassLoader().getResource("Outliers.csv").getFile());
        processor = new CSVProcessor<>(file.toPath());
    }

    @Test
    public void testLoadFileFromResources() {
        assertTrue(file.exists());
    }

    @Test
    public void testProcess() {
        SimpleMovingAverage sma = new SimpleMovingAverage();
        processor.process(sma);
    }

}
