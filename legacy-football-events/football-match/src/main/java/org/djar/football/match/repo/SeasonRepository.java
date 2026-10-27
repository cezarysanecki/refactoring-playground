package org.djar.football.match.repo;

import org.djar.football.match.domain.Season;

public class SeasonRepository {

  private final Season defaultSeason = new Season("1", "Season 1");

  public Season getDefault() {
    return defaultSeason;
  }

}
