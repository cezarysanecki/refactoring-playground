package pl.cezarysanecki.legacyworkhop.external;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

// from external library, cannot be changed!!!
public class SignVerifier {

    private static final String SIG_PARAM = "sig";
    private static final String OFFER_ID_PARAM = "offerId";
    private static final String MEDIUM_PARAM = "medium";
    private static final String SOURCE_PARAM = "source";

    public boolean hasValidSignature(URI url) {
        URIBuilder uriBuilder = new URIBuilder(url);
        List<NameValuePair> params = uriBuilder.getQueryParams();

        String sig = getParamValue(params, SIG_PARAM);
        String offerId = getParamValue(params, OFFER_ID_PARAM);
        String medium = getParamValue(params, MEDIUM_PARAM);
        String source = getParamValue(params, SOURCE_PARAM);

        if (sig == null || offerId == null || medium == null || source == null) {
            return false;
        }

        String expectedSig = createSignature(offerId, medium, source);
        return sig.equals(expectedSig);
    }

    public URI signUri(URI url) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            List<NameValuePair> params = uriBuilder.getQueryParams();

            String offerId = getParamValue(params, OFFER_ID_PARAM);
            String medium = getParamValue(params, MEDIUM_PARAM);
            String source = getParamValue(params, SOURCE_PARAM);

            if (offerId == null || medium == null || source == null) {
                throw new IllegalArgumentException(
                        "URI must contain offerId, medium and source parameters");
            }

            String sig = createSignature(offerId, medium, source);
            uriBuilder.addParameter(SIG_PARAM, sig);

            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URI: " + url, e);
        }
    }

    private String getParamValue(List<NameValuePair> params, String name) {
        return params.stream()
                .filter(p -> p.getName().equals(name))
                .map(NameValuePair::getValue)
                .findFirst()
                .orElse(null);
    }

    private String createSignature(String offerId, String medium, String source) {
        String data = offerId + medium + source;
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

}