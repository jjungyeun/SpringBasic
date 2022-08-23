package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                // 빈 ModelAndView를 반환하면 뷰 렌더링을 수행하지 않고 정상 동작처럼 전달
                return new ModelAndView();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 다음 ExceptionResolver 실행. 없으면 발생한 에러 그대로 전달
        return null;
    }
}
