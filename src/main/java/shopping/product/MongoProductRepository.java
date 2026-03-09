package shopping.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class MongoProductRepository implements ProductRepository {

    private final SpringDataProductRepository repository;

    public MongoProductRepository(SpringDataProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        ProductDocument doc = ProductDocument.fromDomain(product);
        ProductDocument saved = repository.save(doc);
        return saved.toDomain();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return repository.findById(id).map(ProductDocument::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream().map(ProductDocument::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
