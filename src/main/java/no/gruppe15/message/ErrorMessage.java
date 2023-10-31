package no.gruppe15.message;

/**
 * An error message after a command execution which failed.
 */
public class ErrorMessage implements Message {
  private final String message;

  public ErrorMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

}
