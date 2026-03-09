package shopping.product;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class SaveProductService {

    private final ProductRepository productRepository;

    public SaveProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(ProductName productName, long price, String imageUrl,
            ProductStatus status) {
        Product product = new Product(productName, price, imageUrl, status);
        return productRepository.save(product);
    }
}
