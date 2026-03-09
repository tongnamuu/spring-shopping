package shopping.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FindProductServiceTest {

    @Autowired
    private CreateProduct createProduct;

    @Autowired
    private FindProduct findProduct;

    @Test
    void 상품을_조회한다() {
        Product product = createProduct.execute("상품", 1000, "http://image.png");

        Product found = findProduct.execute(product.getId());

        assertEquals("상품", found.getName().getValue());
        assertEquals(1000, found.getPrice());
    }

    @Test
    void 전체_상품을_조회한다() {
        createProduct.execute("상품1", 1000, "http://image1.png");
        createProduct.execute("상품2", 2000, "http://image2.png");

        List<Product> products = findProduct.execute();

        assertTrue(products.size() >= 2);
    }

    @Test
    void 존재하지_않는_상품이면_예외가_발생한다() {
        assertThrows(NoSuchElementException.class, () -> findProduct.execute(UUID.randomUUID()));
    }
}
