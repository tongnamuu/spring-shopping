package shopping.member;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "wish",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "product_id"}))
public class WishEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    protected WishEntity() {}

    public WishEntity(MemberEntity member, UUID productId) {
        this.id = UUID.randomUUID();
        this.member = member;
        this.productId = productId;
    }

    public UUID getProductId() {
        return productId;
    }
}
