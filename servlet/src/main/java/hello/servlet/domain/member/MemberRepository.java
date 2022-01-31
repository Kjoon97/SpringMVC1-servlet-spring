package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 여기서는 동시성 문제가 고려되어있지 않음. 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려해야.
 */

public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();  // 키:아이디, 값:멤버.
    private static long sequence =0L;                          //아이디가 하나씩 증가하는 시퀀스로 사용할것임.
    //static으로 하는 이유: MemberRepository 객체가 new로 아무리 많이 생성되어도 static으로 된 건 하나씩만 생성되게 하려고
    //아래에서 클래스를 싱글톤으로 만들었기 때문에 꼭 굳이 static을 안 써도 되긴하다.

    private static final MemberRepository instance = new MemberRepository(); //싱글톤으로 만들 예정.(지금은 스프링을 사용안하고 있기 때문에)

    public static MemberRepository getInstance(){  //이것으로만 조회 가능.
        return instance;
    }

    private MemberRepository(){  //아무나 생성하는 것을 막으려고, 생성자를 private으로 선언.
    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);  //해쉬맵 store 객체에 저장.
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());   //store에 있는 모든 값들을 다 꺼내서 새로운 ArrayList에 담아서 리턴.
        //이렇게 하는 이유: 새로운 ArrayList에 값을 넣거나 조작해서 store에 있는 value들을 건들이고 싶지 않아서,그래도 value를 수정할 수는 있다 그냥 단지 store자체를 보호하려고.
    }

    public void clearStore(){
        store.clear();  //store해시맵을 다 날림(테스트 시 사용)
    }
}
