package shopping.wish;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import shopping.member.Member;
import shopping.member.MemberRepository;

@SpringBootTest
class AddWishServiceTest {

    @Autowired
    private AddWish addWish;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("addwish@test.com", "password123"));
    }

    @Test
    void 위시리스트에_상품을_추가한다() {
        UUID productId = UUID.randomUUID();

        Wish wish = addWish.execute(member.getId(), productId);

        assertNotNull(wish);
        assertEquals(productId, wish.getProductId());
    }

    @Test
    void 추가한_위시가_저장된다() {
        UUID productId = UUID.randomUUID();

        addWish.execute(member.getId(), productId);

        Member found = memberRepository.findById(member.getId()).orElseThrow();
        assertEquals(1, found.getWishes().size());
        assertEquals(productId, found.getWishes().get(0).getProductId());
    }

    @Test
    void 이미_추가된_상품이면_예외가_발생한다() {
        UUID productId = UUID.randomUUID();
        addWish.execute(member.getId(), productId);

        assertThrows(IllegalArgumentException.class,
                () -> addWish.execute(member.getId(), productId));
    }
}
