package shopping.wish;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wishes")
public class WishDocument {

    @Id
    private UUID memberId;

    private List<UUID> productIds = new ArrayList<>();

    protected WishDocument() {}

    public WishDocument(UUID memberId, List<UUID> productIds) {
        this.memberId = memberId;
        this.productIds = new ArrayList<>(productIds);
    }

    public List<UUID> getProductIds() {
        return productIds;
    }
}
