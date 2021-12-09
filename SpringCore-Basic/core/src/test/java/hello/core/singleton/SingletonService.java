package hello.core.singleton;

public class SingletonService {

    // static 영역에 단 1개만 생성해둔다.
    private static final SingletonService instance = new SingletonService();

    // 객체 인스턴스가 필요하면 오직 이 static getter를 통해서만 조회하도록 한다.
    public static SingletonService getInstance() {
        return instance;
    }

    // private 생성자를 만들어, 다른 곳에서 생성(new)하지 못하도록 막는다.
    private SingletonService(){
    }

    public void logic(){
        System.out.println("싱글통 객체 로직 호출");
    }
}
