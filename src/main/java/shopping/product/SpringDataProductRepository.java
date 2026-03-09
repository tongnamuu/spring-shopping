package shopping.product;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataProductRepository extends MongoRepository<ProductDocument, UUID> {
}
