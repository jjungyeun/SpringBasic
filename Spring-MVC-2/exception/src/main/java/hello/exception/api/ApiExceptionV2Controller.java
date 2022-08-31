package hello.exception.api;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    // 현재 컨트롤러에서 IllegalArgumentException이 발생하면 ExceptionHandlerExceptionResolver가 이 메소드를 실행해줌
    // 예외를 잡아서 정상 흐름으로 바꾸어줌 (서블릿에서 다시 내려오지 않음)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler //(UserException.class) -> 메소드 파라미터에 있는 예외랑 똑같으면 생략 가능
    public ResponseEntity<ErrorResult> userExHandler(UserException e){
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return ResponseEntity.badRequest().body(errorResult);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(
            @PathVariable String id
    ){
        if (id.equals("ex")){
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")){
            throw new IllegalArgumentException("잘못된 입력");
        }
        if (id.equals("user-ex")){
            throw new UserException("사용자 오류");
        }
        return new MemberDto(id, "hello" + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
