package shopping;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import shopping.product.ProfanityChecker;

@Component
@Primary
public class FakeProfanityChecker implements ProfanityChecker {

    @Override
    public boolean containsProfanity(String text) {
        return false;
    }
}
