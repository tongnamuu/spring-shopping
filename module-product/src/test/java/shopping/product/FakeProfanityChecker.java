package shopping.product;

import java.util.Set;

public class FakeProfanityChecker implements ProfanityChecker {

    private final Set<String> profanities;
    private final Set<String> unknowns;

    public FakeProfanityChecker() {
        this(Set.of(), Set.of());
    }

    public FakeProfanityChecker(String... profanities) {
        this(Set.of(profanities), Set.of());
    }

    private FakeProfanityChecker(Set<String> profanities, Set<String> unknowns) {
        this.profanities = profanities;
        this.unknowns = unknowns;
    }

    public static FakeProfanityChecker unknownFor(String... texts) {
        return new FakeProfanityChecker(Set.of(), Set.of(texts));
    }

    @Override
    public ProfanityCheckResult check(String text) {
        if (profanities.stream().anyMatch(text::contains)) {
            return ProfanityCheckResult.PROFANE;
        }
        if (unknowns.stream().anyMatch(text::contains)) {
            return ProfanityCheckResult.UNKNOWN;
        }
        return ProfanityCheckResult.CLEAN;
    }
}
