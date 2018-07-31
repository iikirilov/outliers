package reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.Assert.assertTrue;

public class CSVProcessorTest {

    private final ObjectMapper om = new ObjectMapper();

    private File file;

    @Before
    public void loadFileFromResources() {
        file = new File(getClass().getClassLoader().getResource("Outliers.csv").getFile());
    }

    @Test
    public void testLoadFileFromResources() {
        assertTrue(file.exists());
    }

    @Test
    public void testProcess() throws IOException {

        CSVParser parser = new CSVParser(Files.newBufferedReader(file.toPath()), CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());

        for (CSVRecord record: parser) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
            LocalDate dateTime = LocalDate.parse(record.get("date"), formatter);
            float price = Float.parseFloat(record.get("price"));
            System.out.println(dateTime + " : " + price);
        }

//        CSVProcessor.process(file.toPath(), dpd -> {
//
//        });
    }

}
