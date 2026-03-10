package shopping.product;

public class ReviewPendingProductsService implements ReviewPendingProducts {

    private final ProductRepository productRepository;
    private final ProfanityChecker profanityChecker;

    public ReviewPendingProductsService(ProductRepository productRepository,
            ProfanityChecker profanityChecker) {
        this.productRepository = productRepository;
        this.profanityChecker = profanityChecker;
    }

    @Override
    public PendingProductReviewResult execute() {
        int reviewedCount = 0;
        int approvedCount = 0;
        int rejectedCount = 0;
        int pendingCount = 0;

        for (Product product : productRepository.findAll()) {
            if (!product.isPendingReview()) {
                continue;
            }

            reviewedCount++;
            switch (profanityChecker.check(product.getName().getValue())) {
                case CLEAN -> {
                    product.approve();
                    productRepository.save(product);
                    approvedCount++;
                }
                case PROFANE -> {
                    product.reject();
                    productRepository.save(product);
                    rejectedCount++;
                }
                case UNKNOWN -> pendingCount++;
            }
        }

        return new PendingProductReviewResult(reviewedCount, approvedCount, rejectedCount,
                pendingCount);
    }
}
