package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    // JPA 스펙때문에 필요한 기본 생성자 (public or protected)
    // 외부에서 사용하지 못하도록 protected로 설정
    protected Address() {

    }

    public String fullAddress(){
        return String.format("%s %s, %s", city, street, zipcode);
    }
}