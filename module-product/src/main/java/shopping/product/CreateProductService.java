package shopping.product;

public class CreateProductService implements CreateProduct {

    private final ProductNameFactory productNameFactory;
    private final SaveProductService saveProductService;
    private final ProfanityChecker profanityChecker;

    public CreateProductService(ProductNameFactory productNameFactory,
            SaveProductService saveProductService, ProfanityChecker profanityChecker) {
        this.productNameFactory = productNameFactory;
        this.saveProductService = saveProductService;
        this.profanityChecker = profanityChecker;
    }

    @Override
    public Product execute(String name, long price, String imageUrl) {
        ProductName productName = productNameFactory.create(name);
        ProductStatus status = checkProfanity(name);
        return saveProductService.execute(productName, price, imageUrl, status);
    }

    private ProductStatus checkProfanity(String name) {
        try {
            return profanityChecker.containsProfanity(name) ? ProductStatus.PENDING
                    : ProductStatus.CREATED;
        } catch (Exception e) {
            return ProductStatus.PENDING;
        }
    }
}
