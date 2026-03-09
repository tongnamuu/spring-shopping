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
        SaveProductService saveProductService = new SaveProductService(productRepository);
        service = new CreateProductService(nameFactory, saveProductService,
                new FakeProfanityChecker());
    }

    @Test
    void 상품을_생성한다() {
        Product product = service.execute("상품", 1000, "http://image.png");

        assertNotNull(product.getId());
        assertEquals("상품", product.getName().getValue());
        assertEquals(1000, product.getPrice());
        assertEquals("http://image.png", product.getImageUrl());
        assertEquals(ProductStatus.CREATED, product.getStatus());
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
    void 비속어가_포함되면_PENDING_상태로_저장된다() {
        CreateProductService serviceWithProfanity = new CreateProductService(
                new ProductNameFactory(), new SaveProductService(productRepository),
                new FakeProfanityChecker("badword"));

        Product product = serviceWithProfanity.execute("badword", 1000, "http://image.png");

        assertEquals(ProductStatus.PENDING, product.getStatus());
    }

    @Test
    void 외부_API_실패시_PENDING_상태로_저장된다() {
        CreateProductService serviceWithFailure = new CreateProductService(new ProductNameFactory(),
                new SaveProductService(productRepository), text -> {
                    throw new RuntimeException("API failure");
                });

        Product product = serviceWithFailure.execute("상품", 1000, "http://image.png");

        assertEquals(ProductStatus.PENDING, product.getStatus());
    }
}
