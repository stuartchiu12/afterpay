package afterpay;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class FullTest {

    private Runner runner = new Runner();

    @Test
    public void testWithTestCSV(){

        var reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("test.csv"));
        var list = runner.run(reader,100);
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("a", list.get(0));
    }


}
