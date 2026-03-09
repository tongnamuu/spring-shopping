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
    public SaveProductService saveProductService(ProductRepository productRepository) {
        return new SaveProductService(productRepository);
    }

    @Bean
    public ModifyProductService modifyProductService(ProductRepository productRepository) {
        return new ModifyProductService(productRepository);
    }

    @Bean
    public CreateProduct createProduct(ProductNameFactory productNameFactory,
            SaveProductService saveProductService, ProfanityChecker profanityChecker) {
        return new CreateProductService(productNameFactory, saveProductService, profanityChecker);
    }

    @Bean
    public FindProduct findProduct(ProductRepository productRepository) {
        return new FindProductService(productRepository);
    }

    @Bean
    public UpdateProduct updateProduct(ProductNameFactory productNameFactory,
            ModifyProductService modifyProductService, ProfanityChecker profanityChecker) {
        return new UpdateProductService(productNameFactory, modifyProductService, profanityChecker);
    }

    @Bean
    public DeleteProduct deleteProduct(ProductRepository productRepository) {
        return new DeleteProductService(productRepository);
    }
}
