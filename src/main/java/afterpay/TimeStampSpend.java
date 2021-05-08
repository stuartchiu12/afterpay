package afterpay;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeStampSpend {
    @Override
    public String toString() {
        return dateTime+" "+dollarAndCents;
    }

    private LocalDateTime dateTime;
    private double dollarAndCents;

    public TimeStampSpend(String timestamp, String price){
        dateTime = DateTimeFormatter.ISO_DATE_TIME.parse(timestamp, LocalDateTime::from);
         dollarAndCents= Double.parseDouble(price);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getDollarAndCents() {
        return dollarAndCents;
    }

    public void setDollarAndCents(double dollarAndCents) {
        this.dollarAndCents = dollarAndCents;
    }
}
