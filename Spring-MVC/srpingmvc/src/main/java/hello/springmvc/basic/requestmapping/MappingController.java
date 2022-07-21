package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MappingController {

    @RequestMapping(value = {"/hello-basic", "/hello-go"}, method = RequestMethod.GET)
    public String helloBasic(){
        log.info("helloBasic");
        return "ok";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1(){
        log.info("mappingGetV1");
        return "ok";
    }

    // method 생략한 애노테이션 사용
    @GetMapping("/mapping-get-v2")
    public String mappingGetV2(){
        log.info("mappingGetV2");
        return "ok";
    }

    // Path Variable 사용
    // 변수명이 같으면 value 생략 가능
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data){
        log.info("mappingPath userId={}", data);
        return "ok";
    }

    // Path Variable 여러개 사용
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath2(@PathVariable String userId, @PathVariable Long orderId){
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam(){
        log.info("mappingParam");
        return "ok";
    }

    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader(){
        log.info("mappingHeader");
        return "ok";
    }

    // consume: header의 Content-Type
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsume(){
        log.info("mappingConsume");
        return "ok";
    }

    // produce: header의 Accept
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduce(){
        log.info("mappingProduce");
        return "ok";
    }


}
