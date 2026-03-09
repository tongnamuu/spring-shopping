package shopping.product;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DeleteProductServiceTest {

    @Autowired
    private CreateProduct createProduct;

    @Autowired
    private DeleteProduct deleteProduct;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 상품을_삭제한다() {
        Product product = createProduct.execute("상품", 1000, "http://image.png");

        deleteProduct.execute(product.getId());

        assertTrue(productRepository.findById(product.getId()).isEmpty());
    }

    @Test
    void 존재하지_않는_상품이면_예외가_발생한다() {
        assertThrows(NoSuchElementException.class, () -> deleteProduct.execute(UUID.randomUUID()));
    }
}
