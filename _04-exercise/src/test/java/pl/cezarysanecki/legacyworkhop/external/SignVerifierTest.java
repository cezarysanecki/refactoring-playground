package pl.cezarysanecki.legacyworkhop.external;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignVerifierTest {

    private SignVerifier sut = new SignVerifier();

    @Test
    void shouldSignUriWithAllRequiredParameters() {
        // given
        URI uri = URI.create("https://example.com?offerId=123&medium=email&source=google");

        // when
        URI signedUri = sut.signUri(uri);

        // then
        assertTrue(signedUri.toString().contains("sig="));
    }

    @Test
    void shouldThrowExceptionWhenSigningUriWithoutOfferId() {
        // given
        URI uri = URI.create("https://example.com?medium=email&source=google");

        // when & then
        assertThrows(IllegalArgumentException.class, () -> sut.signUri(uri));
    }

    @Test
    void shouldThrowExceptionWhenSigningUriWithoutMedium() {
        // given
        URI uri = URI.create("https://example.com?offerId=123&source=google");

        // when & then
        assertThrows(IllegalArgumentException.class, () -> sut.signUri(uri));
    }

    @Test
    void shouldThrowExceptionWhenSigningUriWithoutSource() {
        // given
        URI uri = URI.create("https://example.com?offerId=123&medium=email");

        // when & then
        assertThrows(IllegalArgumentException.class, () -> sut.signUri(uri));
    }

    @Test
    void shouldValidateSignedUri() {
        // given
        URI uri = URI.create("https://example.com?offerId=123&medium=email&source=google");
        URI signedUri = sut.signUri(uri);

        // when
        boolean isValid = sut.hasValidSignature(signedUri);

        // then
        assertTrue(isValid);
    }

    @Test
    void shouldRejectUriWithTamperedSignature() {
        // given
        URI tamperedUri = URI.create(
                "https://example.com?offerId=123&medium=email&source=google&sig=INVALID");

        // when
        boolean isValid = sut.hasValidSignature(tamperedUri);

        // then
        assertFalse(isValid);
    }

    @Test
    void shouldRejectUriWithTamperedOfferId() {
        // given
        URI originalUri = URI.create("https://example.com?offerId=123&medium=email&source=google");
        URI signedUri = sut.signUri(originalUri);

        String tamperedUriString = signedUri.toString().replace("offerId=123", "offerId=HACKED");
        URI tamperedUri = URI.create(tamperedUriString);

        // when
        boolean isValid = sut.hasValidSignature(tamperedUri);

        // then
        assertFalse(isValid);
    }

    @Test
    void shouldRejectUriWithoutSignature() {
        // given
        URI uriWithoutSig = URI.create("https://example.com?offerId=123&medium=email&source=google");

        // when
        boolean isValid = sut.hasValidSignature(uriWithoutSig);

        // then
        assertFalse(isValid);
    }

    @Test
    void shouldRejectUriWithMissingParameters() {
        // given
        URI incompleteUri = URI.create("https://example.com?offerId=123&sig=abc");

        // when
        boolean isValid = sut.hasValidSignature(incompleteUri);

        // then
        assertFalse(isValid);
    }

    @Test
    void shouldPreserveOriginalParametersAfterSigning() {
        // given
        URI uri = URI.create("https://example.com?offerId=ABC&medium=web&source=direct");

        // when
        URI signedUri = sut.signUri(uri);

        // then
        String signedUriString = signedUri.toString();
        assertTrue(signedUriString.contains("offerId=ABC"));
        assertTrue(signedUriString.contains("medium=web"));
        assertTrue(signedUriString.contains("source=direct"));
    }

}
