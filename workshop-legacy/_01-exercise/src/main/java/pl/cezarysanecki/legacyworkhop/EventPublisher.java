package pl.cezarysanecki.legacyworkhop;

public interface EventPublisher<T> {

    void publish(T event);

}
