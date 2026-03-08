package shopping.wish;

public class RemoveWishService implements RemoveWish {

    private final WishRepository wishRepository;

    public RemoveWishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    @Override
    public void execute(Long memberId, Long productId) {
        wishRepository.deleteByMemberIdAndProductId(memberId, productId);
    }
}
