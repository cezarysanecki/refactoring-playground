package pl.cezarysanecki.legacyworkhop.signature;

import pl.cezarysanecki.legacyworkhop.external.SignVerifier;

import java.net.URI;

public class ExternalSignatureVerifier implements SignatureVerifier {

    private final SignVerifier signVerifier;

    public ExternalSignatureVerifier(SignVerifier signVerifier) {
        this.signVerifier = signVerifier;
    }

    @Override
    public boolean hasValidSignature(URI url) {
        return signVerifier.hasValidSignature(url);
    }

}
