package hello.typeconverter.formatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class MyNumberFormatterTest {

    MyNumberFormatter formatter = new MyNumberFormatter();

    @Test
    public void parse() throws Exception{
        Number result = formatter.parse("1,000", Locale.KOREA);
        Assertions.assertEquals(1000L, result);
    }

    @Test
    public void print() throws Exception{
        String result = formatter.print(10000, Locale.KOREA);
        Assertions.assertEquals("10,000", result);
    }
}