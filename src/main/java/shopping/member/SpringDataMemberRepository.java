package shopping.member;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMemberRepository extends MongoRepository<MemberDocument, UUID> {

    Optional<MemberDocument> findByEmail(String email);

    boolean existsByEmail(String email);
}
