package pl.cezarysanecki.demo.app.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cezarysanecki.demo.app._1.ServiceOne;
import pl.cezarysanecki.demo.app._2.ServiceTwo;

@Component
@RequiredArgsConstructor
public class MainService {

    private final ServiceOne serviceOne;
    private final ServiceTwo serviceTwo;

    public String doSth() {
        return serviceOne.doSth() + " " + serviceTwo.doSth();
    }

}
