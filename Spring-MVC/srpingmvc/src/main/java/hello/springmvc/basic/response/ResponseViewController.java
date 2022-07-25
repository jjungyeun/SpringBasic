package hello.springmvc.basic.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ResponseViewController {

    // ModelAndView 객체를 생성하여 반환하는 방법
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1(){
        return new ModelAndView("response/hello")
                .addObject("data", "hello!");
    }

    // model에 데이터를 넣어주고 view의 이름을 반환하는 방법
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model){
        model.addAttribute("data", "hello!");
        return "response/hello";
    }

    // 경로로 view template을 찾도록 하는 방법 -> 권장하지 않음!
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model){
        model.addAttribute("data", "hello!");
    }


}
