package org.djar.football.match.domain;

import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(force = true)
public class Goal {

  private final String id;
  private final String matchId;
  private final int minute;
  private final String scorerId;
  private final Team scoredFor;

  Goal(String id, String matchId, int minute, String scorerId, Team scoredFor) {
    this.id = Objects.requireNonNull(id);
    this.matchId = matchId;
    this.minute = minute;
    this.scorerId = scorerId;
    this.scoredFor = scoredFor;
  }

  public String getId() {
    return id;
  }

  public String getMatchId() {
    return matchId;
  }

  public int getMinute() {
    return minute;
  }

  public String getScorerId() {
    return scorerId;
  }

  public Team getScoredFor() {
    return scoredFor;
  }
}
