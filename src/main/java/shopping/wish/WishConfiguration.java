package shopping.wish;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shopping.member.MemberRepository;

@Configuration
public class WishConfiguration {

    @Bean
    public AddWish addWish(MemberRepository memberRepository, WishRepository wishRepository) {
        return new AddWishService(memberRepository, wishRepository);
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
