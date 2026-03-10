package shopping.product;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "product.review.pending.enabled", havingValue = "true",
        matchIfMissing = true)
public class PendingProductReviewScheduler {

    private final ReviewPendingProducts reviewPendingProducts;

    public PendingProductReviewScheduler(ReviewPendingProducts reviewPendingProducts) {
        this.reviewPendingProducts = reviewPendingProducts;
    }

    @Scheduled(fixedDelayString = "${product.review.pending.fixed-delay:60000}",
            initialDelayString = "${product.review.pending.initial-delay:60000}")
    public void recheckPendingProducts() {
        reviewPendingProducts.execute();
    }
}
