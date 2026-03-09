package shopping.member;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import shopping.wish.SpringDataWishRepository;
import shopping.wish.Wish;
import shopping.wish.WishDocument;

@Repository
public class CompositeMemberRepository implements MemberRepository {

    private final SpringDataMemberRepository jpaRepository;
    private final SpringDataWishRepository wishRepository;

    public CompositeMemberRepository(SpringDataMemberRepository jpaRepository,
            SpringDataWishRepository wishRepository) {
        this.jpaRepository = jpaRepository;
        this.wishRepository = wishRepository;
    }

    @Override
    public Member save(Member member) {
        MemberEntity entity = MemberEntity.fromDomain(member);
        jpaRepository.save(entity);
        WishDocument wishDoc = new WishDocument(member.getId(),
                member.getWishes().stream().map(Wish::getProductId).toList());
        wishRepository.save(wishDoc);
        return member;
    }

    @Override
    public Optional<Member> findById(UUID id) {
        return jpaRepository.findById(id).map(entity -> withWishes(entity.toDomain()));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(entity -> withWishes(entity.toDomain()));
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    private Member withWishes(Member member) {
        wishRepository.findById(member.getId()).ifPresent(doc -> {
            for (UUID productId : doc.getProductIds()) {
                member.wish(productId);
            }
        });
        return member;
    }
}
