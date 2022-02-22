package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController // = @Controller @ResponseBody 두 어노테이션을 합친 것
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 1. Request를 엔티티 형태로 그대로 받음 (엔티티를 외부로 노출)
     * - 장점: 추가적인 클래스 정의 불필요
     * - 단점: 너무 많음!
     */
    @PostMapping("/api/v1/member")
    public CreateMemberResponse saveMemberV1(@RequestBody Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 2. Request를 위한 DTO를 생성
     * - 장점: 엔티티를 변경해도 API 스펙이 변경되지 않음,
     *       엔티티에 Presentation Layer를 위한 코드를 넣지 않아도 됨 (@NotEmpty 등)
     */
    @PostMapping("/api/v2/member")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = Member.createMember(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }
}
