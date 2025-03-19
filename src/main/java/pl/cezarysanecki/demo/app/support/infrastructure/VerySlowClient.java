package pl.cezarysanecki.demo.app.support.infrastructure;

import org.springframework.stereotype.Component;

@Component
public class VerySlowClient {

    public String json() {
        throw new UnsupportedOperationException("not implemented by client");
    }

}
