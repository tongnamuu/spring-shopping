package shopping.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReviewPendingProductsServiceTest {

    private InMemoryProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
    }

    @Test
    void 검토_대기_상품을_승인한다() {
        Product product = productRepository.save(new Product(new ProductName("검토대기"), 1000,
                "http://img.png", ProductModerationStatus.PENDING_REVIEW));
        ReviewPendingProductsService service =
                new ReviewPendingProductsService(productRepository, new FakeProfanityChecker());

        PendingProductReviewResult result = service.execute();

        assertEquals(1, result.reviewedCount());
        assertEquals(1, result.approvedCount());
        assertEquals(ProductModerationStatus.APPROVED,
                productRepository.findById(product.getId()).orElseThrow().getModerationStatus());
    }

    @Test
    void 검토_대기_상품을_거절한다() {
        Product product = productRepository.save(new Product(new ProductName("badword"), 1000,
                "http://img.png", ProductModerationStatus.PENDING_REVIEW));
        ReviewPendingProductsService service = new ReviewPendingProductsService(productRepository,
                new FakeProfanityChecker("badword"));

        PendingProductReviewResult result = service.execute();

        assertEquals(1, result.reviewedCount());
        assertEquals(1, result.rejectedCount());
        assertEquals(ProductModerationStatus.REJECTED,
                productRepository.findById(product.getId()).orElseThrow().getModerationStatus());
    }

    @Test
    void 비속어_검사를_여전히_할_수_없으면_검토_대기_상태를_유지한다() {
        Product product = productRepository.save(new Product(new ProductName("검토대기"), 1000,
                "http://img.png", ProductModerationStatus.PENDING_REVIEW));
        ReviewPendingProductsService service = new ReviewPendingProductsService(productRepository,
                FakeProfanityChecker.unknownFor("검토대기"));

        PendingProductReviewResult result = service.execute();

        assertEquals(1, result.reviewedCount());
        assertEquals(1, result.pendingCount());
        assertEquals(ProductModerationStatus.PENDING_REVIEW,
                productRepository.findById(product.getId()).orElseThrow().getModerationStatus());
    }
}
