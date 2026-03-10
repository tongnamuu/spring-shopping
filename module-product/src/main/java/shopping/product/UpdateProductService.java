package shopping.product;

import java.util.NoSuchElementException;
import java.util.UUID;

public class UpdateProductService implements UpdateProduct {

    private final ProductRepository productRepository;
    private final ProductNameFactory productNameFactory;
    private final ProfanityChecker profanityChecker;

    public UpdateProductService(ProductRepository productRepository,
            ProductNameFactory productNameFactory, ProfanityChecker profanityChecker) {
        this.productRepository = productRepository;
        this.productNameFactory = productNameFactory;
        this.profanityChecker = profanityChecker;
    }

    @Override
    public Product execute(UUID id, String name, long price, String imageUrl) {
        ProductName productName = productNameFactory.create(name);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다."));
        product.update(productName, price, imageUrl, resolveModerationStatus(name));
        return productRepository.save(product);
    }

    private ProductModerationStatus resolveModerationStatus(String name) {
        return switch (profanityChecker.check(name)) {
            case CLEAN -> ProductModerationStatus.APPROVED;
            case PROFANE -> throw new IllegalArgumentException("상품 이름에 비속어가 포함되어 있습니다.");
            case UNKNOWN -> ProductModerationStatus.PENDING_REVIEW;
        };
    }
}
