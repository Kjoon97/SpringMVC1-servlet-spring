package hello.servlet.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {

    private Long id;   //repository에 save()할때 아이디를 set 할거임. 멤버 생성할 때는 아이디 x
    private String username;
    private int age;

    public Member(){

    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
