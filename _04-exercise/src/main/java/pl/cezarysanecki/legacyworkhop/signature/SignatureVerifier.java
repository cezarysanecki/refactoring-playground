package pl.cezarysanecki.legacyworkhop.signature;

import java.net.URI;

public interface SignatureVerifier {

    boolean hasValidSignature(URI url);

}
