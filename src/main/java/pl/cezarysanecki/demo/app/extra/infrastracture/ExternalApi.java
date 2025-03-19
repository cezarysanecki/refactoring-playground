package pl.cezarysanecki.demo.app.extra.infrastracture;

import org.springframework.stereotype.Component;
import pl.cezarysanecki.demo.utils.helpers.Sth;

@Component
public class ExternalApi {

    public String getSth(int id) {
        return "sth";
    }

}
