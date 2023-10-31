package no.gruppe15.message;

/**
 * A message saying "all OK, the command was executed successfully".
 */
public record OkMessage(String message) implements Message {

}
