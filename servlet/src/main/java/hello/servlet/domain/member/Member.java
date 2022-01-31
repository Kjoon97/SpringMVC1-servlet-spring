package hello.servlet.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {

    private Long id;   //repository에 저장하면 id가 발급될 것임.
    private String username;
    private int age;

    public Member(){

    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
