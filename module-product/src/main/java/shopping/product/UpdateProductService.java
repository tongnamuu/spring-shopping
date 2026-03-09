package shopping.product;

import java.util.UUID;

public class UpdateProductService implements UpdateProduct {

    private final ProductNameFactory productNameFactory;
    private final ModifyProductService modifyProductService;
    private final ProfanityChecker profanityChecker;

    public UpdateProductService(ProductNameFactory productNameFactory,
            ModifyProductService modifyProductService, ProfanityChecker profanityChecker) {
        this.productNameFactory = productNameFactory;
        this.modifyProductService = modifyProductService;
        this.profanityChecker = profanityChecker;
    }

    @Override
    public Product execute(UUID id, String name, long price, String imageUrl) {
        ProductName productName = productNameFactory.create(name);
        ProductStatus status = checkProfanity(name);
        return modifyProductService.execute(id, productName, price, imageUrl, status);
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
