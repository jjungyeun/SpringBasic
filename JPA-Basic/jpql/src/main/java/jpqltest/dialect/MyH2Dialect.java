package jpqltest.dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MyH2Dialect extends H2Dialect {
    public MyH2Dialect() {
        registerFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }

    // 사용자 정의 함수를 위한 Custom Dialect 설정
    // persistence.xml에 H2Dialect -> MyH2Dialect로 변경해야 사용 가능
}
