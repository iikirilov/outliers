package core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base holder for a date and price pair
 */
public abstract class DatePriceBase implements DatePrice {

    private static final Logger log = LoggerFactory.getLogger(DatePriceBase.class);
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    // The formatter to parse date object available in superclass
    DateTimeFormatter formatter;

    private LocalDate date;
    private double price;

    public DatePriceBase() {
        this(null, DEFAULT_DATE_FORMAT, 0);
    }

    public DatePriceBase(final String dateFormat) {
        this(null, dateFormat, 0);
    }

    public DatePriceBase(final LocalDate date, final double price) {
        this(date, DEFAULT_DATE_FORMAT, price);
    }

    public DatePriceBase(final LocalDate date, final String dateFormat, final double price) {
        this.date = date;
        this.price = price;
        this.formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);
    }

    /**
     * Method to attempt to parse a date and price from the input object.
     * @param o The object to attempt to parse from
     * @param <T> The type of the object to attempt to parse from
     */
    abstract <T> void parse(T o);

    @Override
    public void setDate(final String date) {
        this.date = LocalDate.parse(date, formatter);
    }

    @Override
    public String getDate() {
        return date.format(formatter);
    }

    @Override
    public void setPrice(final String price) {
        this.price = Double.parseDouble(price);
    }

    @Override
    public String getPrice() {
        return String.valueOf(price);
    }

    public double getPriceAsDouble() {
        return price;
    }
}
