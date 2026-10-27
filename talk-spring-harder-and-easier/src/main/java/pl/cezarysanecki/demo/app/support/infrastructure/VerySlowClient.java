package pl.cezarysanecki.demo.app.support.infrastructure;

import org.springframework.stereotype.Component;
import pl.cezarysanecki.demo.app.support.ExternalClient;

@Component
public class VerySlowClient implements ExternalClient {

    @Override
    public String json() {
        throw new UnsupportedOperationException("not implemented by client");
    }

}
