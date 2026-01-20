package pl.cezarysanecki.legacyworkhop;

import org.junit.jupiter.api.Test;
import pl.cezarysanecki.legacyworkhop.external.SignVerifier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class PolicyPurchaseServiceTest {

    private static final InMemoryOfferRepository offerRepository = new InMemoryOfferRepository();

    private PolicyPurchaseService sut = new PolicyPurchaseService(
            new SignVerifier(),
            offerRepository
    );

    @Test
    void shouldReturnOfferExpiredWhenOfferIsExpired() {
        // given

        // when

        // then

    }

    @Test
    void shouldGeneratePolicyNumberWhenOfferIsValid() {
        // given

        // when

        // then

    }

    private static class InMemoryOfferRepository implements OfferRepository {

        private final Map<String, Offer> offers = new ConcurrentHashMap<>();

        void save(Offer offer) {
            offers.put(offer.id(), offer);
        }

        @Override
        public Offer findById(String offerId) {
            return offers.get(offerId);
        }
    }

}
