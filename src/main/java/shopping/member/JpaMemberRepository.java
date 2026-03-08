package shopping.member;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class JpaMemberRepository implements MemberRepository {

    private final SpringDataMemberRepository repository;

    public JpaMemberRepository(SpringDataMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public Member save(Member member) {
        MemberEntity entity = MemberEntity.fromDomain(member);
        MemberEntity saved = repository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Member> findById(UUID id) {
        return repository.findById(id).map(MemberEntity::toDomain);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return repository.findByEmail(email).map(MemberEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
