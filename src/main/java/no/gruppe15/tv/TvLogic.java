package no.gruppe15.tv;

import no.gruppe15.tv.gui.SmartTVController;

/**
 * This class represents the Smart TV logic.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 30.10.2023
 */
public class TvLogic {
  private boolean isTvOn;
  private final int numberOfChannels;
  private int currentChannel;
  private SmartTVController controller;

  /**
   * Create a new Smart TV.
   *
   * @param numberOfChannels The total number of channels the TV has
   */
  public TvLogic(int numberOfChannels) {
    if (numberOfChannels < 1) {
      throw new IllegalArgumentException("Number of channels must be positive");
    }

    this.numberOfChannels = numberOfChannels;
    isTvOn = false;
    currentChannel = 1;
  }

  /**
   * Turn ON the TV.
   */
  public void turnOn() {
    if (isTvOn) {
      throw new IllegalStateException("The TV is already on");
    }
    controller.setChannelMedia("1");
    isTvOn = true;
  }

  /**
   * Turn OFF the TV.
   */
  public void turnOff() {
    if (!isTvOn) {
      throw new IllegalStateException("The TV must be turned on first");
    }
    controller.setChannelMedia("0");
    isTvOn = false;
  }

  /**
   * Check whether the TV is ON or OFF.
   *
   * @return True when the TV is ON, false when OFF.
   */
  public boolean isTvOn() {
    return isTvOn;
  }

  /**
   * Get the number of channels this TV has.
   *
   * @return The total number of channels
   * @throws IllegalStateException When the TV is OFF
   */
  public int getNumberOfChannels() throws IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException("The TV must be turned on first");
    }
    controller.displayNumberOfChannels(numberOfChannels);
    return numberOfChannels;
  }

  /**
   * Get the current channel of the TV.
   *
   * @return The current channel
   * @throws IllegalStateException When the TV is OFF
   */
  public int getCurrentChannel() throws IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException("The TV must be turned on first");
    }
    return currentChannel;
  }

  /**
   * Set the channel for the TV.
   *
   * @param channel The desired channel
   * @throws IllegalArgumentException When the channel number is invalid
   * @throws IllegalStateException    When the TV is OFF
   */
  public void setChannel(int channel) throws IllegalArgumentException, IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException("The TV must be turned on first");
    }
    if (channel <= 0 || channel > numberOfChannels) {
      throw new IllegalArgumentException("Invalid channel number");
    }
    handleController(channel);
    currentChannel = channel;
  }

  /**
   * This method connects the GUI controller to the tv logic.
   *
   * @param controller The app controller.
   */
  public void setController(SmartTVController controller){
    this.controller = controller;
  }

  /**
   * This method handles commands for the controller.
   *
   * @param command Current command.
   */
  public void handleController(int command){
    controller.setChannelMedia(command + "");
  }
}
