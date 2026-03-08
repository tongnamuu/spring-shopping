package shopping.wish;

import java.util.List;

public class FindWishService implements FindWish {

    private final WishRepository wishRepository;

    public FindWishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    @Override
    public List<Wish> execute(Long memberId) {
        return wishRepository.findByMemberId(memberId);
    }
}
