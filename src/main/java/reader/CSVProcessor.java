package reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Processes CSV files with two columns named `date` and `price` respectively
 */
public class CSVProcessor {

    private static final Logger log = LoggerFactory.getLogger(CSVProcessor.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);


    public static void process(final Path path, final BiFunction<DatePriceDto, Context, DatePriceDto> function) {
        CSVParser parser = null;
        CSVPrinter csvPrinter = null;
        try {
            parser = new CSVParser(Files.newBufferedReader(path), CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());

            Path out = Paths.get(path.getParent().toString() + "out.csv");

            File tmp = new File(out.toUri());
            tmp.createNewFile();
            BufferedWriter writer = Files.newBufferedWriter(out);

            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Date", "Price"));
        } catch (IOException e) {
            log.error("File not found and path: {}", path);
        }

        Context context = new Context(10,0L,0.0f);
        for (CSVRecord record: parser) {
            LocalDate dateTime = LocalDate.parse(record.get("date"), formatter);
            float price = Float.parseFloat(record.get("price"));

            DatePriceDto processed = function.apply(new DatePriceDto(dateTime, price), context);
            if(processed != null) {
                try {
                    csvPrinter.printRecord(processed.getLocalDate(), processed.getPrice());
                } catch (IOException e) {
                    log.error("Failed to write record to file: {} , {}",
                            processed.getLocalDate(), processed.getPrice());
                }
            }

        }
    }
}
