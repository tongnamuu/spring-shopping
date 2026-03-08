package shopping.member;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMemberRepository extends JpaRepository<MemberEntity, UUID> {

    Optional<MemberEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
