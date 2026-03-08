package shopping.member;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<WishEntity> wishes = new ArrayList<>();

    protected MemberEntity() {}

    public static MemberEntity fromDomain(Member member) {
        MemberEntity entity = new MemberEntity();
        entity.id = member.getId();
        entity.email = member.getEmail();
        entity.password = member.getPassword();
        for (Wish wish : member.getWishes()) {
            entity.wishes.add(new WishEntity(entity, wish.getProductId()));
        }
        return entity;
    }

    public Member toDomain() {
        Member member = new Member(this.id, this.email, this.password);
        for (WishEntity wish : this.wishes) {
            member.wish(wish.getProductId());
        }
        return member;
    }
}
