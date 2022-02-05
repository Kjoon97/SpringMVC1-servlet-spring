package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

   // @RequestMapping(value = "/new-form" , method = RequestMethod.GET)  //단순 조회는 GET으로함.
    @GetMapping("/new-form")
    public String newForm(){
        return "new-form";
    }

  //  @RequestMapping(value = "/save" , method = RequestMethod.POST)
    @PostMapping("/save")
    public String save(
            @RequestParam("username") String username,   //"username"이라는 파라미터를 받아와서 String형 usrname에 저장
            @RequestParam("age") int age,
            Model model) {

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member",member);

        return "save-result";
    }

    //@RequestMapping(method = RequestMethod.GET)  //단순조회는 GET으로함.
    @GetMapping
    public String members(Model model) {

        List<Member> members = memberRepository.findAll();
        model.addAttribute("allMembers", members);

        return "members";
    }

}
