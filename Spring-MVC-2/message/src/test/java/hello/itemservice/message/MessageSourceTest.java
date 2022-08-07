package hello.itemservice.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    private MessageSource ms;

    @Test
    public void helloMessage() throws Exception{
        // locale 정보가 없기 때문에 기본 메시지 파일 (messages.properties를 사용함)
        String result = ms.getMessage("hello", null, null);
        assertEquals("안녕", result);
    }

    @Test
    public void notFoundMessageCode() throws Exception{
        // 메시지 파일에서 찾을 수 없으면 NoSuchMessageException 발생
        assertThrows(NoSuchMessageException.class, () -> ms.getMessage("no_code", null, null));
    }

    @Test
    public void notCode() throws Exception{
        // default message 지정 가능
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertEquals("기본 메시지", result);
    }

    @Test
    public void argumentMessage() throws Exception{
        // arguments에 Object 배열을 넘김
        String result = ms.getMessage("hello.name", new Object[]{"Spring!"}, null);
        assertEquals("안녕 Spring!", result);
    }

    @Test
    public void defaultLang() throws Exception{
        assertEquals("안녕", ms.getMessage("hello", null, null));
        assertEquals("안녕", ms.getMessage("hello", null, Locale.KOREA));
    }

    @Test
    public void enLang() throws Exception{
        assertEquals("Hello", ms.getMessage("hello", null, Locale.ENGLISH));
    }
}
