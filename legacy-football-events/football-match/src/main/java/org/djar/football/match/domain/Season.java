package org.djar.football.match.domain;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(force = true)
public class Season {

  private final String id;
  private final String name;

  public Season(String id, String name) {
    this.id = Objects.requireNonNull(id);
    this.name = name;
  }

  public Match scheduleMatch(String matchId, LocalDateTime date, String homeClubId, String awayClubId) {
    return new Match(matchId, id, date, new Team(homeClubId), new Team(awayClubId));
  }

  public Player startCareer(String playerId, String name) {
    return new Player(playerId, name);
  }

  @Override
  public String toString() {
    return name;
  }
}
