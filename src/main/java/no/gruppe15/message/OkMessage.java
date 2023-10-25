package no.gruppe15.message;

/**
 * A message saying "all OK, the command was executed successfully".
 */
public class OkMessage extends Message {

  private String feedback;

  public OkMessage(String feedback) {
    this.feedback = feedback;
  }

  public String getMessage() {
    return feedback;
  }
}
