package shopping.wish;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WishConfiguration {

    @Bean
    public AddWish addWish(WishRepository wishRepository) {
        return new AddWishService(wishRepository);
    }

    @Bean
    public RemoveWish removeWish(WishRepository wishRepository) {
        return new RemoveWishService(wishRepository);
    }

    @Bean
    public FindWish findWish(WishRepository wishRepository) {
        return new FindWishService(wishRepository);
    }
}
