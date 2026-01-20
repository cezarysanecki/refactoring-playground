package pl.cezarysanecki.legacyworkhop;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Stats {

    @Id
    private Long id;

    private UUID accountId;

    private Long views;
    private Long likes;

    public Stats() {
    }

    public Stats(Long id, UUID accountId, Long views, Long likes) {
        this.id = id;
        this.accountId = accountId;
        this.views = views;
        this.likes = likes;
    }

    public Stats(UUID accountId, Long views, Long likes) {
        this.accountId = accountId;
        this.views = views;
        this.likes = likes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }
}
