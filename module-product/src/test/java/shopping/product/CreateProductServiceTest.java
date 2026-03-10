package shopping.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateProductServiceTest {

    private InMemoryProductRepository productRepository;
    private CreateProductService service;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        ProductNameFactory nameFactory = new ProductNameFactory();
        service = new CreateProductService(productRepository, nameFactory,
                new FakeProfanityChecker());
    }

    @Test
    void 상품을_생성한다() {
        Product product = service.execute("상품", 1000, "http://image.png");

        assertNotNull(product.getId());
        assertEquals("상품", product.getName().getValue());
        assertEquals(1000, product.getPrice());
        assertEquals("http://image.png", product.getImageUrl());
        assertEquals(ProductModerationStatus.APPROVED, product.getModerationStatus());
    }

    @Test
    void 생성된_상품이_저장소에_저장된다() {
        Product product = service.execute("상품", 1000, "http://image.png");

        Product found = productRepository.findById(product.getId()).orElseThrow();
        assertEquals(product.getId(), found.getId());
    }

    @Test
    void 유효하지_않은_이름이면_예외가_발생한다() {
        assertThrows(IllegalArgumentException.class,
                () -> service.execute("", 1000, "http://image.png"));
    }

    @Test
    void 비속어가_포함되면_예외가_발생한다() {
        ProductNameFactory nameFactory = new ProductNameFactory();
        CreateProductService profanityService = new CreateProductService(productRepository,
                nameFactory, new FakeProfanityChecker("badword"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> profanityService.execute("badword", 1000, "http://image.png"));

        assertEquals("상품 이름에 비속어가 포함되어 있습니다.", exception.getMessage());
    }

    @Test
    void 비속어_검사가_불가능하면_검토_대기_상태로_생성한다() {
        ProductNameFactory nameFactory = new ProductNameFactory();
        CreateProductService pendingReviewService = new CreateProductService(productRepository,
                nameFactory, FakeProfanityChecker.unknownFor("검토대기"));

        Product product = pendingReviewService.execute("검토대기", 1000, "http://image.png");

        assertEquals(ProductModerationStatus.PENDING_REVIEW, product.getModerationStatus());
    }
}
