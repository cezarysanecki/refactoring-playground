package org.djar.football.match.domain;

import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(force = true)
public class Player {

  private final String id;
  private final String name;

  Player(String id, String name) {
    this.id = Objects.requireNonNull(id);
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}
