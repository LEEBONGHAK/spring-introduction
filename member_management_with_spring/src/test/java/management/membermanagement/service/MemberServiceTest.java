package management.membermanagement.service;

import management.membermanagement.domain.Member;
import management.membermanagement.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // 각 test 전에 실행됨
    @BeforeEach
    public void beforeEach() {
        // Dependency Injection (DI)
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    // 영어권 사람들이랑 작업하지 않을 때는 테스트 코드의 경우 한글로도 사용하는 경우가 있음
    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);

        // then1: 방법 1 try-catch
//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        // then2: 방법2 assertThrows()
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void 전체_회원_조회() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        memberService.join(member1);
        
        Member member2 = new Member();
        member2.setName("spring2");
        memberService.join(member2);

        // when
        List<Member> memberList = memberService.findMember();

        // then
        assertThat(memberList.size()).isEqualTo(2);
    }

    @Test
    void 회원_한명_찾기() {
        // given
        Member member1 = new Member();
        member1.setName("spring");
        Long member1Id = memberService.join(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        Long join = memberService.join(member2);

        // when
        Member result = memberService.findOne(member1Id).get();

        // then
        assertThat(member1).isEqualTo(result);
        assertThat(member2).isNotEqualTo(result);
    }
}