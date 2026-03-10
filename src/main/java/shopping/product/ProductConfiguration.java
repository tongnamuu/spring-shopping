package shopping.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfiguration {

    @Bean
    public ProductNameFactory productNameFactory() {
        return new ProductNameFactory();
    }

    @Bean
    public CreateProduct createProduct(ProductRepository productRepository,
            ProductNameFactory productNameFactory, ProfanityChecker profanityChecker) {
        return new CreateProductService(productRepository, productNameFactory, profanityChecker);
    }

    @Bean
    public FindProduct findProduct(ProductRepository productRepository) {
        return new FindProductService(productRepository);
    }

    @Bean
    public UpdateProduct updateProduct(ProductRepository productRepository,
            ProductNameFactory productNameFactory, ProfanityChecker profanityChecker) {
        return new UpdateProductService(productRepository, productNameFactory, profanityChecker);
    }

    @Bean
    public DeleteProduct deleteProduct(ProductRepository productRepository) {
        return new DeleteProductService(productRepository);
    }

    @Bean
    public ReviewPendingProducts reviewPendingProducts(ProductRepository productRepository,
            ProfanityChecker profanityChecker) {
        return new ReviewPendingProductsService(productRepository, profanityChecker);
    }
}
