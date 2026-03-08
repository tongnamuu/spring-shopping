package shopping.member;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class MongoMemberRepository implements MemberRepository {

    private final SpringDataMemberRepository repository;

    public MongoMemberRepository(SpringDataMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public Member save(Member member) {
        MemberDocument document = MemberDocument.fromDomain(member);
        MemberDocument saved = repository.save(document);
        return saved.toDomain();
    }

    @Override
    public Optional<Member> findById(UUID id) {
        return repository.findById(id).map(MemberDocument::toDomain);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return repository.findByEmail(email).map(MemberDocument::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
