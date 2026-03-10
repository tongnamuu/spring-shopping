package shopping.wish.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "wish")
public class Wish {

    @Id
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "wished_price", nullable = false)
    private long wishedPrice;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Wish() {}

    public Wish(UUID productId, long wishedPrice) {
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.wishedPrice = wishedPrice;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getWishedPrice() {
        return wishedPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
