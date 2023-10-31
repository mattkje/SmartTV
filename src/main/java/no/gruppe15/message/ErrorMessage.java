package no.gruppe15.message;

/**
 * An error message after a command execution which failed.
 */
public record ErrorMessage(String message) implements Message {

}
