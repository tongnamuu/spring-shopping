package shopping.product;

import java.util.UUID;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    private UUID id;

    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "name", nullable = false, length = 15))
    private ProductName name;

    @Column(nullable = false)
    private long price;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", nullable = false, length = 20)
    private ProductModerationStatus moderationStatus;

    protected Product() {}

    public Product(ProductName name, long price, String imageUrl) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.moderationStatus = ProductModerationStatus.APPROVED;
    }

    public Product(ProductName name, long price, String imageUrl,
            ProductModerationStatus moderationStatus) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.moderationStatus = moderationStatus;
    }

    public void update(ProductName name, long price, String imageUrl) {
        update(name, price, imageUrl, ProductModerationStatus.APPROVED);
    }

    public void update(ProductName name, long price, String imageUrl,
            ProductModerationStatus moderationStatus) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.moderationStatus = moderationStatus;
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

    public ProductModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public boolean isApproved() {
        return moderationStatus == ProductModerationStatus.APPROVED;
    }

    public boolean isPendingReview() {
        return moderationStatus == ProductModerationStatus.PENDING_REVIEW;
    }

    public boolean isRejected() {
        return moderationStatus == ProductModerationStatus.REJECTED;
    }

    public void approve() {
        moderationStatus = ProductModerationStatus.APPROVED;
    }

    public void markPendingReview() {
        moderationStatus = ProductModerationStatus.PENDING_REVIEW;
    }

    public void reject() {
        moderationStatus = ProductModerationStatus.REJECTED;
    }
}
