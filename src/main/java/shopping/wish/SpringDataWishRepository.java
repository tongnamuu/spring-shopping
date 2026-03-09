package shopping.wish;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataWishRepository extends MongoRepository<WishDocument, UUID> {
}
