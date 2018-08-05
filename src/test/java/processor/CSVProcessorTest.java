package processor;

import algo.SimpleMovingAverage;
import org.apache.commons.csv.CSVFormat;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CSVProcessorTest {

    private Path in;
    private CSVProcessor<SimpleMovingAverage> processor;

    @Before
    public void setUpContext() {
        in = Paths.get(getClass().getClassLoader().getResource("OutliersTest.csv").getPath());
        processor = new CSVProcessor<>(in);
    }

    @Test
    public void testLoadFileFromResources() {
        assertTrue(new File(in.toUri()).exists());
    }

    @Test
    public void testProcess() throws IOException {
        SimpleMovingAverage sma = new SimpleMovingAverage();
        processor.process(sma);

        File out = new File(getClass().getClassLoader().getResource(CSVProcessor.DEFAULT_OUTPUT_FILE_NAME).getFile());
        String data = new String(Files.readAllBytes(out.toPath()));

        String recordSeparator = CSVFormat.DEFAULT.getRecordSeparator();
        char deliminator = CSVFormat.DEFAULT.getDelimiter();

        assertThat(data, is("Date" + deliminator + "Price" + recordSeparator +
                "09/01/1990" + deliminator + "1.45" + recordSeparator +
                "10/01/1990" + deliminator + "1.76" + recordSeparator +
                "11/01/1990" + deliminator + "1.64" + recordSeparator +
                "12/01/1990" + deliminator + "1.83" + recordSeparator +
                "15/01/1990" + deliminator + "1.62" + recordSeparator +
                "09/01/1990" + deliminator + "1.75" + recordSeparator +
                "10/01/1990" + deliminator + "1.66" + recordSeparator +
                "11/01/1990" + deliminator + "1.74" + recordSeparator +
                "12/01/1990" + deliminator + "1.73" + recordSeparator +
                "15/01/1990" + deliminator + "1.62" + recordSeparator +
                "12/01/1990" + deliminator + "1.63" + recordSeparator +
                "15/01/1990" + deliminator + "1.62" + recordSeparator));


    }

}
