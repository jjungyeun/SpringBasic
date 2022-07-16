package hello.servlet.web.frontcontroller.v4;

import java.util.Map;

public interface ControllerV4 {
    /**
     *
     * @param paramMap - 파라미터 맵
     * @param model - 데이터를 담을 model
     * @return viewName
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
