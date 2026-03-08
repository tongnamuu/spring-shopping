package shopping.member;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import shopping.wish.Wish;

@Entity
@Table(name = "member")
public class MemberEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "member_wish", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "product_id")
    private List<UUID> wishProductIds = new ArrayList<>();

    protected MemberEntity() {}

    public static MemberEntity fromDomain(Member member) {
        MemberEntity entity = new MemberEntity();
        entity.id = member.getId();
        entity.email = member.getEmail();
        entity.password = member.getPassword();
        entity.wishProductIds = member.getWishes().stream().map(Wish::getProductId)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return entity;
    }

    public Member toDomain() {
        Member member = new Member(this.id, this.email, this.password);
        for (UUID productId : this.wishProductIds) {
            member.wish(productId);
        }
        return member;
    }
}
