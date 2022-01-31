package hello.servlet.domain.memeber;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest {
    MemberRepository memberRepository = MemberRepository.getInstance();  //싱글톤이므로 new MemberRepository()로는 호출(생성)이 안됨

    @AfterEach //테스트 끝날 때마다 해시맵 비우기., 테스트를 한번에 돌리면 각 테스트의 순서는 보장이 안되기 때문에 무조건 쓴다.
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void save(){  //멤버 새로 만들어 저장하고, 저장되면 다시 꺼내와서 같은 객체인지 비교.
        //given
        Member member = new Member("joon",20);
        //when
        Member savedMember = memberRepository.save(member);
        //then
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll(){  //멤버 2명을 새로 만들어 저장하고, 모두 불러온 리스트 객체의 크기가 멤버 수랑 맞는지 확인, 멤버들을 모두 포함하는지 확인.
        //given
        Member member1 = new Member("member1",20);
        Member member2 = new Member("member2",30);
        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> allMember = memberRepository.findAll();

        //then
        assertThat(allMember.size()).isEqualTo(2);
        assertThat(allMember).contains(member1,member2);

    }
}
