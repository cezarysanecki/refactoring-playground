package org.djar.football.match.controller;

public class NotFoundException extends RuntimeException {

  private final Object id;

  public NotFoundException(String message, Object id) {
    super(message + ": " + id);
    this.id = id;
  }

  public Object getId() {
    return id;
  }
}
