package reader;

import java.time.LocalDate;

public class DatePriceDto {

    private final LocalDate localDate;
    private final float price;

    public DatePriceDto(final LocalDate localDate, final float price) {
        this.localDate = localDate;
        this.price = price;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public float getPrice() {
        return price;
    }
}
