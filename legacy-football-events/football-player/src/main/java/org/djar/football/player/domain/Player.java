package org.djar.football.player.domain;

import java.util.Objects;

public class Player {

  private final String id;
  private final String name;

  public Player(String id, String name) {
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
