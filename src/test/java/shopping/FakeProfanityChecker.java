package shopping;

import java.util.Set;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import shopping.product.ProfanityCheckResult;
import shopping.product.ProfanityChecker;

@Component
@Primary
public class FakeProfanityChecker implements ProfanityChecker {

    private static final Set<String> BAD_WORDS = Set.of("badword", "profanity");
    private static final Set<String> UNKNOWN_WORDS = Set.of("pending review");
    private volatile ProfanityCheckResult pendingReviewResult = ProfanityCheckResult.UNKNOWN;

    public void reset() {
        pendingReviewResult = ProfanityCheckResult.UNKNOWN;
    }

    public void resolvePendingReviewAsClean() {
        pendingReviewResult = ProfanityCheckResult.CLEAN;
    }

    public void resolvePendingReviewAsProfane() {
        pendingReviewResult = ProfanityCheckResult.PROFANE;
    }

    @Override
    public ProfanityCheckResult check(String text) {
        String lower = text.toLowerCase();
        if (BAD_WORDS.stream().anyMatch(lower::contains)) {
            return ProfanityCheckResult.PROFANE;
        }
        if (UNKNOWN_WORDS.stream().anyMatch(lower::contains)) {
            return pendingReviewResult;
        }
        return ProfanityCheckResult.CLEAN;
    }
}
