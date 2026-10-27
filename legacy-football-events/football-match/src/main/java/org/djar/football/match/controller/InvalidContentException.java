package org.djar.football.match.controller;

public class InvalidContentException extends RuntimeException {

  public InvalidContentException(String msg, Object itemId) {
    super(msg + (itemId == null ? "" : ": " + itemId));
  }
}
