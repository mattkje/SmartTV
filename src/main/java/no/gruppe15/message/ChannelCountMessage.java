package no.gruppe15.message;

/**
 * A message containing the number of channels for the TV.
 */
public record ChannelCountMessage(int channelCount) implements Message {
}
