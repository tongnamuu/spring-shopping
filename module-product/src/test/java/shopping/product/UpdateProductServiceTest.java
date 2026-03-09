package shopping.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpdateProductServiceTest {

    private InMemoryProductRepository productRepository;
    private UpdateProductService service;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        ProductNameFactory nameFactory = new ProductNameFactory();
        ModifyProductService modifyProductService = new ModifyProductService(productRepository);
        service = new UpdateProductService(nameFactory, modifyProductService,
                new FakeProfanityChecker());
    }

    @Test
    void 상품을_수정한다() {
        Product saved = productRepository.save(
                new Product(new ProductName("상품"), 1000, "http://img.png", ProductStatus.CREATED));

        Product updated = service.execute(saved.getId(), "수정상품", 2000, "http://new.png");

        assertEquals(saved.getId(), updated.getId());
        assertEquals("수정상품", updated.getName().getValue());
        assertEquals(2000, updated.getPrice());
        assertEquals("http://new.png", updated.getImageUrl());
        assertEquals(ProductStatus.CREATED, updated.getStatus());
    }

    @Test
    void 수정된_상품이_저장소에_반영된다() {
        Product saved = productRepository.save(
                new Product(new ProductName("상품"), 1000, "http://img.png", ProductStatus.CREATED));

        service.execute(saved.getId(), "수정상품", 2000, "http://new.png");

        Product found = productRepository.findById(saved.getId()).orElseThrow();
        assertEquals("수정상품", found.getName().getValue());
    }

    @Test
    void 존재하지_않는_상품을_수정하면_예외가_발생한다() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> service.execute(UUID.randomUUID(), "상품", 1000, "http://img.png"));

        assertEquals("상품이 존재하지 않습니다.", exception.getMessage());
    }

    @Test
    void 유효하지_않은_이름으로_수정하면_예외가_발생한다() {
        Product saved = productRepository.save(
                new Product(new ProductName("상품"), 1000, "http://img.png", ProductStatus.CREATED));

        assertThrows(IllegalArgumentException.class,
                () -> service.execute(saved.getId(), "", 2000, "http://new.png"));
    }

    @Test
    void 비속어가_포함되면_PENDING_상태로_수정된다() {
        Product saved = productRepository.save(
                new Product(new ProductName("상품"), 1000, "http://img.png", ProductStatus.CREATED));
        UpdateProductService serviceWithProfanity = new UpdateProductService(
                new ProductNameFactory(), new ModifyProductService(productRepository),
                new FakeProfanityChecker("badword"));

        Product updated =
                serviceWithProfanity.execute(saved.getId(), "badword", 2000, "http://new.png");

        assertEquals(ProductStatus.PENDING, updated.getStatus());
    }
}
