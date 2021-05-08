package afterpay;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class InvalidTest {

    private Runner runner = new Runner();

    @Test
    public void testWhen24HoursUnderThreshold(){
        var list = List.of(new TimeStampSpend("2014-01-01T00:00:00", "10.0"),
                new TimeStampSpend("2014-01-01T23:59:59", "10.0"),
                new TimeStampSpend("2014-01-02T00:10:00", "10.0"));

        Assertions.assertTrue(runner.isValid(20, list));
        Assertions.assertFalse(runner.isValid(19, list));

    }

    @Test
    public void testOneElement(){
        var list = List.of(new TimeStampSpend("2014-01-01T00:00:00", "10.0"));

        Assertions.assertTrue(runner.isValid(11, list));
        Assertions.assertTrue(runner.isValid(10, list));
        Assertions.assertFalse(runner.isValid(9, list));
    }

    @Test
    public void testNoElement(){
        var list =  Collections.<TimeStampSpend>emptyList();
        Assertions.assertTrue(runner.isValid(1, list));
    }
}
