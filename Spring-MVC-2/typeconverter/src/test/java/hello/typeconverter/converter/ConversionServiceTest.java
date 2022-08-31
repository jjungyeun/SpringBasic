package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionServiceTest {

    @Test
    public void conversionService() throws Exception{
        // 등록
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        // 사용
        Assertions.assertEquals(10, conversionService.convert("10", Integer.class));
        Assertions.assertEquals("10", conversionService.convert(10, String.class));

        IpPort ipPort = new IpPort("127.0.0.1", 8080);
        Assertions.assertEquals("127.0.0.1:8080", conversionService.convert(ipPort, String.class));
        Assertions.assertEquals(ipPort, conversionService.convert("127.0.0.1:8080", IpPort.class));
    }
}
