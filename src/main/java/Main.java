import algo.SimpleMovingAverage;
import processor.CSVProcessor;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    Main() {
        Path in = Paths.get(getClass().getClassLoader().getResource("Outliers.csv").getPath());
        CSVProcessor<SimpleMovingAverage> processor = new CSVProcessor<>(in);
        processor.process(new SimpleMovingAverage());
    }

    public static void main(String[] args) {
        new Main();
    }
}
