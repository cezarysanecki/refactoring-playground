package org.djar.football.match.domain;

import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(force = true)
public class Card {

  public enum Type {
    YELLOW, RED
  }

  private final String id;
  private final String matchId;
  private final int minute;
  private final String receiverId;
  private final Type type;

  Card(String id, String matchId, int minute, String receiverId, Type type) {
    this.id = Objects.requireNonNull(id);
    this.matchId = matchId;
    this.minute = minute;
    this.receiverId = receiverId;
    this.type = type;
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

  public String getReceiverId() {
    return receiverId;
  }

  public Type getType() {
    return type;
  }
}
