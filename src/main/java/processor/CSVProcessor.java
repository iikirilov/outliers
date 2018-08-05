package processor;

import algo.ContextAwareAlogorithm;
import core.CSVDatePrice;
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

/**
 * Processes CSV files with two columns named `date` and `price` respectively
 */
public class CSVProcessor<E extends ContextAwareAlogorithm> implements Processor<E>{

    private static final Logger log = LoggerFactory.getLogger(CSVProcessor.class);
    public static final double DEFAULT_LOWER_BOUND = 0.9f;
    public static final double DEFAULT_UPPER_BOUND = 1.1f;
    public static final String DEFAULT_OUTPUT_FILE_NAME = "out.csv";

    private final double lowerBound;
    private final double upperBound;

    CSVParser parser;
    CSVPrinter printer;

    public CSVProcessor(final Path path) {
        this(path, path.getParent().toString(), DEFAULT_OUTPUT_FILE_NAME,
                DEFAULT_LOWER_BOUND, DEFAULT_UPPER_BOUND);
    }

    public CSVProcessor(final Path path, final String outputFilePath) {
        this(path, outputFilePath, DEFAULT_OUTPUT_FILE_NAME,
                DEFAULT_LOWER_BOUND, DEFAULT_UPPER_BOUND);
    }

    public CSVProcessor(final Path path, final double lowerBound, final double upperBound) {
        this(path, path.getParent().toString(), DEFAULT_OUTPUT_FILE_NAME,
                lowerBound, upperBound);
    }

    public CSVProcessor(final Path path, final String outputFilePath,
                        final double lowerBound, final double upperBound) {
        this(path, outputFilePath, DEFAULT_OUTPUT_FILE_NAME,
                lowerBound, upperBound);
    }

    public CSVProcessor(final Path path, final String outputFilePath, final String outPutFileName,
                        final double lowerBound, final double upperBound) {
        try {
            parser = new CSVParser(Files.newBufferedReader(path), CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());

            Path out = Paths.get(outputFilePath + "/" +outPutFileName);

            File tmp = new File(out.toUri());
            tmp.createNewFile();
            BufferedWriter writer = Files.newBufferedWriter(out);
            log.info("Printer set up to: {}", out.toUri());
            printer = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Date", "Price"));

            printer.printComment("output");
        } catch (IOException e) {
            log.error("File not found and path: {}", path);
        }
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    @Override
    public void process(final E algo) {
        CSVDatePrice template = new CSVDatePrice();

        for (CSVRecord record: parser) {
            template.parse(record);
            algo.nextInput(template.getPriceAsDouble());
            if (algo.getIteration() == 0) {
                printRecord(template);
            } else {
                if (algo.output() * lowerBound <= template.getPriceAsDouble()
                        && algo.output() * upperBound >= template.getPriceAsDouble()) {
                    printRecord(template);
                } else {
                    algo.undo();
                    log.info("Value out of bound on line: {}", parser.getCurrentLineNumber());
                }
            }
        }
        try {
            printer.flush();
        } catch (IOException e) {
            log.error("Failed to flush CSVPrinter");
        }
    }

    private void printRecord(final CSVDatePrice template) {
        try {
            printer.printRecord(template.getDate(), template.getPrice());
        } catch (IOException e) {
            log.error("Failed to write record to file: {} , {}",
                    template.getDate(), template.getPrice());
        }
    }
}
