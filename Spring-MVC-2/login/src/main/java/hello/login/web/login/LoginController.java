package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class LoginController {
    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form){
        return "login/loginForm";
    }

//    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){
        if (bindingResult.hasErrors())
            return "login/loginForm";

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);

        // 글로벌 오류 - 객체 자체만으로는 검증할 수 없음. 이 경우에도 DB까지 뒤져봐야 확인 가능함.
        if (loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리

        // 쿠키에 시간 정보를 주지 않으면 세션 쿠키로 설정됨 (브라우저 종료 시 모두 종료)
        Cookie idCookie = new Cookie("memberId", loginMember.getId().toString());
        response.addCookie(idCookie);

        return "redirect:/";
    }

//    @PostMapping("/login")
    public String loginV2(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){
        if (bindingResult.hasErrors())
            return "login/loginForm";

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);

        // 글로벌 오류 - 객체 자체만으로는 검증할 수 없음. 이 경우에도 DB까지 뒤져봐야 확인 가능함.
        if (loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리
        // 세션 관리자를 통해 세션을 생성하고, 회원 데이터를 보관
        sessionManager.createSession(loginMember, response);

        return "redirect:/";
    }

//    @PostMapping("/login")
    public String loginV3(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request){
        if (bindingResult.hasErrors())
            return "login/loginForm";

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);

        // 글로벌 오류 - 객체 자체만으로는 검증할 수 없음. 이 경우에도 DB까지 뒤져봐야 확인 가능함.
        if (loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리

        // 세션이 있으면 있는 세션을 반환하고, 없으면 신규 세션을 생성해서 반환
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보를 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginV4(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult,
                          @RequestParam(defaultValue = "/") String redirectURL,
                          HttpServletRequest request){
        if (bindingResult.hasErrors())
            return "login/loginForm";

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);

        // 글로벌 오류 - 객체 자체만으로는 검증할 수 없음. 이 경우에도 DB까지 뒤져봐야 확인 가능함.
        if (loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리

        // 세션이 있으면 있는 세션을 반환하고, 없으면 신규 세션을 생성해서 반환
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보를 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:" + redirectURL;
    }

//    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        expireCookie(response, "memberId");
        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request){
        sessionManager.expire(request);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);    // expired time을 0으로 설정하면 됨
        response.addCookie(cookie);
    }
}
