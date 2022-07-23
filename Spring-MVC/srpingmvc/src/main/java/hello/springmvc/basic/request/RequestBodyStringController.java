package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    // Http Body에 단순 Text가 넘어올 때 받는 방법

    // 1. HttpServletRequest에서 InputStream을 꺼내서 String으로 변환해서 사용하는 방법
    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        response.getWriter().write("ok");
    }

    // 2. InputStream을 직접 받아서 String으로 변환해서 사용하는 방법
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    // 3. HttpEntity를 받아서 사용하는 방법
    // HttpEntity 내의 타입으로 Http body 데이터를 변환해줌
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);
        return new HttpEntity<>("ok");
    }

    // 3-2. HttpEntity를 상속받은 RequestEntity를 받아서 사용하는 방법
    @PostMapping("/request-body-string-v3-2")
    public ResponseEntity<String> requestBodyStringV3_2(RequestEntity<String> requestEntity) throws IOException {
        URI url = requestEntity.getUrl();
        HttpMethod method = requestEntity.getMethod();
        String messageBody = requestEntity.getBody();
        log.info("url={}, method={}", url, method);
        log.info("messageBody={}", messageBody);
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    // 4. @RequestBody 애노테이션 사용
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}
