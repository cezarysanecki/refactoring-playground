package pl.cezarysanecki.legacyworkhop;

import pl.cezarysanecki.legacyworkhop.external.SignVerifier;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

public class PolicyPurchaseService {

    private final SignVerifier signVerifier;
    private final OfferRepository offerRepository;

    public PolicyPurchaseService(SignVerifier signVerifier, OfferRepository offerRepository) {
        this.signVerifier = signVerifier;
        this.offerRepository = offerRepository;
    }

    public PurchaseResult purchasePolicy(URI policyLink) {
        if (!signVerifier.hasValidSignature(policyLink)) {
            return PurchaseResult.invalidLink();
        }

        String offerId = extractOfferId(policyLink);
        Offer offer = offerRepository.findById(offerId);

        if (offer == null) {
            return PurchaseResult.offerNotFound();
        }

        if (offer.expirationDate().isBefore(LocalDate.now())) {
            return PurchaseResult.offerExpired();
        }

        String policyNumber = generatePolicyNumber();
        return PurchaseResult.success(policyNumber);
    }

    private String extractOfferId(URI policyLink) {
        String query = policyLink.getQuery();
        for (String param : query.split("&")) {
            if (param.startsWith("offerId=")) {
                return param.substring("offerId=".length());
            }
        }
        throw new IllegalArgumentException("Missing offerId in link");
    }

    private String generatePolicyNumber() {
        return "POL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public record PurchaseResult(Status status, String policyNumber) {

        public enum Status {
            SUCCESS, INVALID_LINK, OFFER_NOT_FOUND, OFFER_EXPIRED
        }

        static PurchaseResult success(String policyNumber) {
            return new PurchaseResult(Status.SUCCESS, policyNumber);
        }

        static PurchaseResult invalidLink() {
            return new PurchaseResult(Status.INVALID_LINK, null);
        }

        static PurchaseResult offerNotFound() {
            return new PurchaseResult(Status.OFFER_NOT_FOUND, null);
        }

        static PurchaseResult offerExpired() {
            return new PurchaseResult(Status.OFFER_EXPIRED, null);
        }
    }

}
