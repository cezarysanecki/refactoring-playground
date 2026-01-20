package pl.cezarysanecki.legacyworkhop;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.cezarysanecki.legacyworkhop.PolicyPurchaseService.PurchaseResult.Status;
import pl.cezarysanecki.legacyworkhop.signature.SignatureVerifier;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PolicyPurchaseServiceTest {

    private final InMemoryOfferRepository offerRepository = new InMemoryOfferRepository();

    PolicyPurchaseService sut = new PolicyPurchaseService(
            new OkWithValidSignature(),
            offerRepository
    );

    @Test
    void shouldReturnOfferExpiredWhenOfferIsExpired() {
        // given
        String offerId = "OFFER-123";
        offerRepository.save(new Offer(offerId, "Expired Policy", LocalDate.now().minusDays(1)));
        // and
        URI link = URI.create("https://example.com?offerId=" + offerId + "&sig=valid");

        // when
        var result = sut.purchasePolicy(link);

        // then
        assertEquals(Status.OFFER_EXPIRED, result.status());
    }

    @Test
    void shouldGeneratePolicyNumberWhenOfferIsValid() {
        // given
        String offerId = "OFFER-456";
        offerRepository.save(new Offer("OFFER-456", "Valid Policy", LocalDate.now().plusDays(30)));
        // and
        URI link = URI.create("https://example.com?offerId=" + offerId + "&sig=valid");

        // when
        var result = sut.purchasePolicy(link);

        // then
        assertEquals(Status.SUCCESS, result.status());
        assertNotNull(result.policyNumber());
    }

    private static class OkWithValidSignature implements SignatureVerifier {
        @Override
        public boolean hasValidSignature(URI url) {
            URIBuilder uriBuilder = new URIBuilder(url);
            List<NameValuePair> queryParams = uriBuilder.getQueryParams();
            return queryParams.stream()
                    .anyMatch(queryParam ->
                            queryParam.getName().equals("sig") && queryParam.getValue().equals("valid"));
        }
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
