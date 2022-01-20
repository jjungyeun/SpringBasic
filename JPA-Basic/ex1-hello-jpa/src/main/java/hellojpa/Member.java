package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1, allocationSize = 1
)
//@Table(name = "USER") // DB 테이블 이름 지정 가능
public class Member {

    @Id // PK 매핑
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name")   // DB 컬럼 이름 지정 가능
    private String userName;

    private Integer age;

    @Enumerated(EnumType.STRING)    // Enum 타입 매핑 (무조건 STRING 타입 사용!)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)   // DB에서는 보통 DATE, TIME, DATETIME를 구분해서 사용
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", roleType=" + roleType +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", description='" + description + '\'' +
                '}';
    }
}
