package core;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CSVDatePriceTest {

    private Path testPath = Paths.get(getClass().getClassLoader().getResource("OutliersTest.csv").getPath());
    private CSVDatePrice dp;

    @Before
    public void setUp() {
        dp = new CSVDatePrice();
    }

    @Test
    public void testParse() throws IOException {
        CSVParser parser = new CSVParser(Files.newBufferedReader(testPath), CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
        parser.forEach(dp::parse);
    }

    @Test(expected = RuntimeException.class)
    public void testParseThrowsExeption() {
        dp.parse(new Object());
    }
}
