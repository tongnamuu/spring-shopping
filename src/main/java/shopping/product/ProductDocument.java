package shopping.product;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class ProductDocument {

    @Id
    private UUID id;

    private String name;

    private long price;

    private String imageUrl;

    protected ProductDocument() {}

    public static ProductDocument fromDomain(Product product) {
        ProductDocument doc = new ProductDocument();
        doc.id = product.getId();
        doc.name = product.getName().getValue();
        doc.price = product.getPrice();
        doc.imageUrl = product.getImageUrl();
        return doc;
    }

    public Product toDomain() {
        return new Product(this.id, new ProductName(this.name), this.price, this.imageUrl);
    }
}
