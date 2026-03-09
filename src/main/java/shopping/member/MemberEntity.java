package shopping.member;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class MemberEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    protected MemberEntity() {}

    public static MemberEntity fromDomain(Member member) {
        MemberEntity entity = new MemberEntity();
        entity.id = member.getId();
        entity.email = member.getEmail();
        entity.password = member.getPassword();
        return entity;
    }

    public Member toDomain() {
        return new Member(this.id, this.email, this.password);
    }
}
