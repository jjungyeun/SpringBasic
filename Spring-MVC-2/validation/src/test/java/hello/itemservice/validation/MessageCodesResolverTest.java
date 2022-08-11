package hello.itemservice.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.junit.jupiter.api.Assertions.*;

public class MessageCodesResolverTest {
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    public void messageCodesResolverObject() throws Exception{
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");

        assertEquals("required.item", messageCodes[0]);
        assertEquals("required", messageCodes[1]);
    }

    @Test
    public void messageCodesResolverField() throws Exception{
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);

        assertEquals("required.item.itemName",      messageCodes[0]);  // 에러코드 + 객체명 + 필드명
        assertEquals("required.itemName",           messageCodes[1]);  // 에러코드 + 필드명
        assertEquals("required.java.lang.String",   messageCodes[2]);  // 에러코드 + 필드 타입
        assertEquals("required",                    messageCodes[3]);  // 단순 에러코드만
    }
}
