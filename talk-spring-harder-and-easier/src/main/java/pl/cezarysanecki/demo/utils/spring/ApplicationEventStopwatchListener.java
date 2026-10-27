package pl.cezarysanecki.demo.utils.spring;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.ArrayList;
import java.util.List;

public class ApplicationEventStopwatchListener implements ApplicationListener<ApplicationEvent> {

    private final List<Event> events = new ArrayList<>();

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        events.add(new Event(event.getClass().getSimpleName(), event.getTimestamp()));

        if (event instanceof ApplicationReadyEvent) {
            for (int i = 0; i < events.size() - 1; i++) {
                Event first = events.get(i);
                Event second = events.get(i + 1);

                System.out.printf("➡ %-40s: %5s ms%n", first.eventName, (second.timestamp - first.timestamp));
            }
        }
    }

    record Event(String eventName, long timestamp) {
    }
}
