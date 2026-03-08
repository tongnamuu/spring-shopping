package shopping.wish;

import java.util.NoSuchElementException;

import shopping.member.MemberRepository;

public class AddWishService implements AddWish {

    private final MemberRepository memberRepository;
    private final WishRepository wishRepository;

    public AddWishService(MemberRepository memberRepository, WishRepository wishRepository) {
        this.memberRepository = memberRepository;
        this.wishRepository = wishRepository;
    }

    @Override
    public Wish execute(Long memberId, Long productId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."))
                .wish(productId, wishRepository);
    }
}
