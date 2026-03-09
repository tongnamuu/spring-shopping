package shopping.wish;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import shopping.member.Member;
import shopping.member.MemberRepository;

@SpringBootTest
class RemoveWishServiceTest {

    @Autowired
    private AddWish addWish;

    @Autowired
    private RemoveWish removeWish;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("removewish@test.com", "password123"));
    }

    @Test
    void 위시리스트에서_상품을_제거한다() {
        UUID productId = UUID.randomUUID();
        addWish.execute(member.getId(), productId);

        removeWish.execute(member.getId(), productId);

        Member found = memberRepository.findById(member.getId()).orElseThrow();
        assertTrue(found.getWishes().isEmpty());
    }

    @Test
    void 존재하지_않는_상품을_제거해도_예외가_발생하지_않는다() {
        removeWish.execute(member.getId(), UUID.randomUUID());

        Member found = memberRepository.findById(member.getId()).orElseThrow();
        assertTrue(found.getWishes().isEmpty());
    }
}
