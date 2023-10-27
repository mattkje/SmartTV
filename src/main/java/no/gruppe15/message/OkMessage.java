package no.gruppe15.message;

/**
 * A message saying "all OK, the command was executed successfully".
 */
public class OkMessage extends Message {

  private final String feedback;

  public OkMessage(String feedback) {
    this.feedback = feedback;
  }

  @Override
  public String getMessage() {
    return feedback;
  }
}
