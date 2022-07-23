package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    // 요청 파라미터 (request parameter) 조회 방법

    // 1. HttpServletRequest 이용해서 조회
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }

    // 2. @RequestParam 이용해서 조회
    @ResponseBody   // @RestController와 같은 역할. 반환 String을 그냥 Http 응답으로 사용
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                               @RequestParam("age") int memberAge) throws IOException {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    // 3. @RequestParam 이용해서 조회
    //    요청 파라미터 이름과 변수명이 일치하면 ("") 생략 가능
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) throws IOException {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // 4. 단순히 메소드 파라미터로만 넣어서 조회
    //    요청 파라미터 이름과 변수명이 일치하면 @RequestParam도 생략 가능 (String, int 등 단순 타입인 경우만)
    //    required = false로 설정됨
    //    그러나 너무 과도한 생략으로 해당 변수들이 요청 파라미터에서 읽어오는 것인지 직관적으로 알기 힘듦
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username,
                                 int age) throws IOException {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // required 옵션
    // default: true (무조건 있어야 함)
    // required=true인 파라미터가 없으면 400 error 발생
    // 빈 문자열도 값으로 취급하기 때문에 username= 를 보내면 ""가 들어감
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username,
                                 @RequestParam(required = false) Integer age) throws IOException {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // defaultValue 옵션
    // 요청 파라미터가 없으면 설정한 값이 들어감. -> required 옵션이 의미가 없음
    // 빈 문자열이 들어와도 default value 값으로 들어감
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(defaultValue = "guest") String username,
                                       @RequestParam(defaultValue = "-1") int age) throws IOException {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // 요청 파라미터 전체를 Map으로 한번에 받을 수 있음
    // 파라미터마다 값이 1개라고 확신할 수 없다면, MultiValueMap을 사용해야 함
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) throws IOException {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    // 5. @ModelAttribute를 이용해서 조회
    // 객체를 생성하고 요청 파라미터를 가져와서 setter를 실행해서 값을 넣어줌
    // 파라미터 값이 객체 필드의 타입과 맞지 않으면 BindException이 발생함
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData){
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    // 6. @ModelAttribute 생략 가능
    // 단순 타입이 아닌 직접 정의한 객체 타입의 경우 @ModelAttribute를 적용
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData){
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
