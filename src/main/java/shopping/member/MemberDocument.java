package shopping.member;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import shopping.wish.Wish;

@Document(collection = "members")
public class MemberDocument {

    @Id
    private UUID id;

    @Indexed(unique = true)
    private String email;

    private String password;

    private List<UUID> wishProductIds = new ArrayList<>();

    protected MemberDocument() {}

    public static MemberDocument fromDomain(Member member) {
        MemberDocument doc = new MemberDocument();
        doc.id = member.getId();
        doc.email = member.getEmail();
        doc.password = member.getPassword();
        doc.wishProductIds = member.getWishes().stream().map(Wish::getProductId)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return doc;
    }

    public Member toDomain() {
        Member member = new Member(this.id, this.email, this.password);
        for (UUID productId : this.wishProductIds) {
            member.wish(productId);
        }
        return member;
    }
}
