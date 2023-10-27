package no.gruppe15.message;

/**
 * A message saying "all OK, the command was executed successfully".
 */
public class OkMessage implements Message {

  private final String message;

  public OkMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

}
