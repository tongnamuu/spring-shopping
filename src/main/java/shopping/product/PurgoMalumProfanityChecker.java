package shopping.product;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class PurgoMalumProfanityChecker implements ProfanityChecker {

    private final RestClient restClient;

    public PurgoMalumProfanityChecker(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://www.purgomalum.com").build();
    }

    @Override
    public ProfanityCheckResult check(String text) {
        try {
            String result = restClient.get().uri("/service/containsprofanity?text={text}", text)
                    .retrieve().body(String.class);
            if ("true".equalsIgnoreCase(result)) {
                return ProfanityCheckResult.PROFANE;
            }
            if ("false".equalsIgnoreCase(result)) {
                return ProfanityCheckResult.CLEAN;
            }
            return ProfanityCheckResult.UNKNOWN;
        } catch (RestClientException e) {
            return ProfanityCheckResult.UNKNOWN;
        }
    }
}
