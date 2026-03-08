package shopping.wish;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryWishRepository implements WishRepository {

    private final Map<Long, Wish> wishes = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Wish save(Wish wish) {
        long id = sequence.incrementAndGet();
        Wish saved = new Wish(id, wish.getMemberId(), wish.getProductId());
        wishes.put(id, saved);
        return saved;
    }

    @Override
    public List<Wish> findByMemberId(Long memberId) {
        return wishes.values().stream().filter(w -> w.getMemberId().equals(memberId)).toList();
    }

    @Override
    public void deleteByMemberIdAndProductId(Long memberId, Long productId) {
        wishes.entrySet().removeIf(e -> e.getValue().getMemberId().equals(memberId)
                && e.getValue().getProductId().equals(productId));
    }

    @Override
    public boolean existsByMemberIdAndProductId(Long memberId, Long productId) {
        return wishes.values().stream().anyMatch(
                w -> w.getMemberId().equals(memberId) && w.getProductId().equals(productId));
    }
}
