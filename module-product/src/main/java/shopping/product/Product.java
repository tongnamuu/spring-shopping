package shopping.product;

import java.util.UUID;

public class Product {

    private UUID id;
    private ProductName name;
    private long price;
    private String imageUrl;

    public Product(ProductName name, long price, String imageUrl) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    Product(UUID id, ProductName name, long price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void update(ProductName name, long price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
