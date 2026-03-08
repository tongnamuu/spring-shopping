package shopping.product;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false)
    private long price;

    @Column(name = "image_url")
    private String imageUrl;

    protected ProductEntity() {}

    public static ProductEntity fromDomain(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.id = product.getId();
        entity.name = product.getName().getValue();
        entity.price = product.getPrice();
        entity.imageUrl = product.getImageUrl();
        return entity;
    }

    public Product toDomain() {
        return new Product(this.id, new ProductName(this.name), this.price, this.imageUrl);
    }
}
