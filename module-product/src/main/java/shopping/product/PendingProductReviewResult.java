package shopping.product;

public record PendingProductReviewResult(int reviewedCount, int approvedCount, int rejectedCount,
        int pendingCount) {
}
