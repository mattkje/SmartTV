package no.gruppe15.message;

/**
 * An error message after a command execution which failed.
 */
public class ErrorMessage extends Message {
  private final String message;

  public ErrorMessage(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
