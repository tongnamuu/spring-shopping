package shopping.wish;

public class AddWishService implements AddWish {

    private final WishRepository wishRepository;

    public AddWishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    @Override
    public Wish execute(Long memberId, Long productId) {
        if (wishRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw new IllegalArgumentException("이미 위시리스트에 추가된 상품입니다.");
        }
        return wishRepository.save(new Wish(memberId, productId));
    }
}
