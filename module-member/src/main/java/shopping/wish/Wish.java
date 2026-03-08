package shopping.wish;

import java.util.UUID;

public class Wish {

    private UUID productId;

    public Wish(UUID productId) {
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
