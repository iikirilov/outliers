package core;

import java.time.LocalDate;

import org.apache.commons.csv.CSVRecord;

public class CSVDatePrice extends DatePriceBase {

    public CSVDatePrice() {
        super();
    }

    public CSVDatePrice(final String dateFormat) {
        super(dateFormat);
    }

    public CSVDatePrice(final LocalDate date, final double price) {
        super(date, price);
    }

    public CSVDatePrice(final LocalDate date, final String dateFormat, final double price) {
        super(date, dateFormat, price);
    }

    @Override
    public <T> void parse(final T o) {
        final CSVRecord toParse = checkType(o);
        try {
            setDate(toParse.get("date"));
            setPrice(toParse.get("price"));
        } catch(Exception e) {
            throw new RuntimeException("Failed to parse from CSVRecord");
        }
    }

    private <T> CSVRecord checkType(final T o) {
        if(CSVRecord.class.isAssignableFrom(o.getClass())) {
           return (CSVRecord) o;
        } else {
            throw new RuntimeException(o + " of type: " + o.getClass() +
                    ", is not a CSVRecord and cannot be parsed");
        }
    }
}
