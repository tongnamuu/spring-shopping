package shopping.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UpdateProductServiceTest {

    @Autowired
    private CreateProduct createProduct;

    @Autowired
    private UpdateProduct updateProduct;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 상품을_수정한다() {
        Product product = createProduct.execute("상품", 1000, "http://image.png");

        Product updated =
                updateProduct.execute(product.getId(), "수정상품", 2000, "http://updated.png");

        assertEquals("수정상품", updated.getName().getValue());
        assertEquals(2000, updated.getPrice());
        assertEquals("http://updated.png", updated.getImageUrl());
    }

    @Test
    void 수정한_상품이_저장된다() {
        Product product = createProduct.execute("상품", 1000, "http://image.png");

        updateProduct.execute(product.getId(), "수정상품", 2000, "http://updated.png");

        Product found = productRepository.findById(product.getId()).orElseThrow();
        assertEquals("수정상품", found.getName().getValue());
    }

    @Test
    void 존재하지_않는_상품이면_예외가_발생한다() {
        assertThrows(NoSuchElementException.class,
                () -> updateProduct.execute(UUID.randomUUID(), "상품", 1000, "http://image.png"));
    }
}
