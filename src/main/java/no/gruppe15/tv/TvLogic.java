package no.gruppe15.tv;

import no.gruppe15.tv.gui.SmartTvController;

/**
 * This class represents the Smart TV logic.
 *
 * <p>Code Inspiration:
 * The foundation of this class is inspired by the work of Girts Strazdins.
 *
 * @author Matti Kjellstadli, Adrian Johansen, Håkon Karlsen, Di Xie
 * @version 02.11.2023
 * @see <a href="https://github.com/strazdinsg/datakomm-tools/tree/master" target="_blank">External Repository</a>
 */

public class TvLogic {
  private boolean isTvOn;
  private final int numberOfChannels;
  private int currentChannel;
  private SmartTvController controller;
  private final String onMessage;

  /**
   * Creates an instance of TvLogic.
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
    onMessage = "The TV is already on";
  }

  /**
   * This method should set the isTvOn to true.
   */
  public void turnOn() {
    if (isTvOn) {
      throw new IllegalStateException("The TV is already on");
    }
    controller.setChannelMedia("1");
    isTvOn = true;
  }

  /**
   * This method should set the isTvOn to false.
   */
  public void turnOff() {
    if (!isTvOn) {
      throw new IllegalStateException(onMessage);
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
      throw new IllegalStateException(onMessage);
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
      throw new IllegalStateException(onMessage);
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
      throw new IllegalStateException(onMessage);
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
  public void setController(SmartTvController controller) {
    this.controller = controller;
  }

  /**
   * This method handles commands for the controller.
   *
   * @param command Current command.
   */
  public void handleController(int command) {
    controller.setChannelMedia(command + "");
  }

  /**
   * This method checks whether the tv is on, before calling toggleMute method
   * from controller class.
   */
  public boolean toggleMute() {
    if (!isTvOn) {
      throw new IllegalStateException(onMessage);
    }
    return controller.toggleMute();
  }

}
