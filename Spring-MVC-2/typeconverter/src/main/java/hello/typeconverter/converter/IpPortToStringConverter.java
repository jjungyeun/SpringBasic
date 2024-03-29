package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class IpPortToStringConverter implements Converter<IpPort, String> {
    @Override
    public String convert(IpPort source) {
        // IpPort 객체 -> 127.0.0.8:8080
        log.info("convert source={}", source);
        return source.getIp() + ":" + source.getPort();
    }
}
