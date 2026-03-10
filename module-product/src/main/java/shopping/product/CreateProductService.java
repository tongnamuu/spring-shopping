package shopping.product;

public class CreateProductService implements CreateProduct {

    private final ProductRepository productRepository;
    private final ProductNameFactory productNameFactory;
    private final ProfanityChecker profanityChecker;

    public CreateProductService(ProductRepository productRepository,
            ProductNameFactory productNameFactory, ProfanityChecker profanityChecker) {
        this.productRepository = productRepository;
        this.productNameFactory = productNameFactory;
        this.profanityChecker = profanityChecker;
    }

    @Override
    public Product execute(String name, long price, String imageUrl) {
        ProductName productName = productNameFactory.create(name);
        Product product = new Product(productName, price, imageUrl, resolveModerationStatus(name));
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
