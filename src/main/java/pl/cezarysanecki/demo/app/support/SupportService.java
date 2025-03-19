package pl.cezarysanecki.demo.app.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupportService {

    private final ExternalClient externalClient;
    private final ObjectMapper objectMapper;

    public String getName() {
        String json = externalClient.json();
        try {
            Client client = objectMapper.readValue(json, Client.class);
            return client.name();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
