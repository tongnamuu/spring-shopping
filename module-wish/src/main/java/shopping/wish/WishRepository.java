package shopping.wish;

import java.util.List;

public interface WishRepository {

    Wish save(Wish wish);

    List<Wish> findByMemberId(Long memberId);

    void deleteByMemberIdAndProductId(Long memberId, Long productId);

    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
