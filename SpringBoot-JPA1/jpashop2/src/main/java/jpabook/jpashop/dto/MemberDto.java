package jpabook.jpashop.dto;

import jpabook.jpashop.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private Long memberId;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    public MemberDto(Member member) {
        this.memberId = member.getId();
        this.name = member.getName();
        this.city = member.getAddress().getCity();
        this.street = member.getAddress().getStreet();
        this.zipcode = member.getAddress().getZipcode();
    }
}
