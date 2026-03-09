package shopping.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CreateProductServiceTest {

    @Autowired
    private CreateProduct createProduct;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 상품을_생성한다() {
        Product product = createProduct.execute("상품", 1000, "http://image.png");

        assertNotNull(product.getId());
        assertEquals("상품", product.getName().getValue());
        assertEquals(1000, product.getPrice());
        assertEquals("http://image.png", product.getImageUrl());
    }

    @Test
    void 생성한_상품이_저장된다() {
        Product product = createProduct.execute("상품", 1000, "http://image.png");

        Product found = productRepository.findById(product.getId()).orElseThrow();
        assertEquals(product.getId(), found.getId());
        assertEquals("상품", found.getName().getValue());
    }

    @Test
    void 비속어가_포함된_이름이면_예외가_발생한다() {
        assertThrows(IllegalArgumentException.class,
                () -> createProduct.execute("badword", 1000, "http://image.png"));
    }
}
