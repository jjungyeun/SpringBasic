package jpqltest.dto;

public class MemberDto {
    private String name;
    private int age;

    public MemberDto(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
