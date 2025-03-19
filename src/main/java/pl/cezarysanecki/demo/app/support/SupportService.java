package pl.cezarysanecki.demo.app.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cezarysanecki.demo.app.support.infrastructure.VerySlowClient;

@Component
@RequiredArgsConstructor
public class SupportService {

    private final VerySlowClient verySlowClient;
    private final ObjectMapper objectMapper;

    public String getName() {
        String json = verySlowClient.json();
        try {
            Client client = objectMapper.readValue(json, Client.class);
            return client.name();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
