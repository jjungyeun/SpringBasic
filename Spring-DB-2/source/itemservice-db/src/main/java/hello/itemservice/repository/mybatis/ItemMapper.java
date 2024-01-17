package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper // 패키지 이름이 ibatis인데, 이는 mybatis의 옛이름이다.
public interface ItemMapper {

    /**
     * - Mapper 어노테이션을 붙이면 자동으로 구현체를 만들어준다.
     * (MyBatis 스프링 연동 모듈이 프록시 구현체를 만들고 빈으로 등록한다)
     * - 메서드를 호출하면 XML 파일을 읽어 SQL을 실행하고 결과를 돌려준다.
     * - 예외 변환 처리도 해준다. (MyBatis에서 발생한 예외를 스프링 예외로 변환)
     */

    void save(Item item);

    // 파라미터가 여러개인 경우 @Param을 사용해서 파라미터를 구분할 수 있도록 해줘야 한다.
    void update(@Param("id") Long id, @Param("updateParam")ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearch);
}
