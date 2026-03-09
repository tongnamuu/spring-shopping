package shopping.wish;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import shopping.member.Member;
import shopping.member.MemberRepository;

@SpringBootTest
class FindWishServiceTest {

    @Autowired
    private AddWish addWish;

    @Autowired
    private FindWish findWish;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("findwish@test.com", "password123"));
    }

    @Test
    void 위시리스트를_조회한다() {
        UUID productId = UUID.randomUUID();
        addWish.execute(member.getId(), productId);

        List<Wish> wishes = findWish.execute(member.getId());

        assertEquals(1, wishes.size());
        assertEquals(productId, wishes.get(0).getProductId());
    }

    @Test
    void 위시리스트가_비어있으면_빈_목록을_반환한다() {
        List<Wish> wishes = findWish.execute(member.getId());

        assertTrue(wishes.isEmpty());
    }
}
