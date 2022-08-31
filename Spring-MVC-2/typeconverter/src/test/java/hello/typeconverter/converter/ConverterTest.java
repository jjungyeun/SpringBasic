package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConverterTest {
    @Test
    public void stringToInteger() throws Exception{
        // given
        StringToIntegerConverter converter = new StringToIntegerConverter();

        // when
        Integer result = converter.convert("10");

        // then
        Assertions.assertEquals(10, result);
    }

    @Test
    public void integerToString() throws Exception{
        // given
        IntegerToStringConverter converter = new IntegerToStringConverter();

        // when
        String result = converter.convert(10);

        // then
        Assertions.assertEquals("10", result);
    }

    @Test
    public void stringToIpPort() throws Exception{
        // given
        IpPortToStringConverter converter = new IpPortToStringConverter();
        IpPort source = new IpPort("127.0.0.1", 8080);

        // when
        String result = converter.convert(source);

        // then
        Assertions.assertEquals("127.0.0.1:8080", result);
    }

    @Test
    public void ipPortToString() throws Exception{
        // given
        StringToIpPortConverter converter = new StringToIpPortConverter();
        String source = "127.0.0.1:8080";

        // when
        IpPort result = converter.convert(source);

        // then
        Assertions.assertEquals(new IpPort("127.0.0.1", 8080), result);
    }
}
