package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    // Http Body에 단순 Text가 넘어올 때 받는 방법

    private ObjectMapper objectMapper = new ObjectMapper();

    // 1. HttpServletRequest에서 InputStream을 꺼내서 ObjectMapper를 통해 객체로 변환해서 사용하는 방법
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        response.getWriter().write("ok");
    }

    // 2. @RequestBody로 body text를 직접 받아서 ObjectMapper를 통해 객체로 변환해서 사용하는 방법
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    // 3. HttpEntity를 받아서 사용하는 방법
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public HttpEntity<String> requestBodyJsonV3(HttpEntity<HelloData> httpEntity) throws IOException {
        HelloData helloData = httpEntity.getBody();
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return new HttpEntity<>("ok");
    }

    // 4. @RequestBody로 객체 자체를 받아서 사용하는 방법
    // @RequestBody는 생략하면 안됨. 생략하면 @ModelAtrribute로 적용하기 때문에 body가 아니라 요청 파라미터를 처리함.
    // 반환 시에도 HttpMessageConverter가 적용되기 때문에 객체를 그대로 반환할 수 있음 (JSON으로 변환됨)
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public HelloData requestBodyJsonV4(@RequestBody HelloData helloData) throws IOException {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return helloData;
    }
}
