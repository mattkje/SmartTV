package no.gruppe15;

/**
 * Smart TV - the logic.
 */
public class TVLogic {

  private boolean isTvOn;
  private final int numberOfChannels;
  public int currentChannel;


  /**
   * Create a new Smart TV.
   *
   * @param numberOfChannels The total number of channels the TV has    
   */
  public TVLogic(int numberOfChannels) {
    if (numberOfChannels < 1) {
      throw new IllegalArgumentException("Number of channels must be a positive number");
    }

    this.numberOfChannels = numberOfChannels;
    isTvOn = false;
    currentChannel = 1;
  }


  /**
   * Turn on the TV
   * @return tv is on
   */
  public boolean turnOn() {
    return isTvOn = true;
  }

  /**
   * Turn off the TV
   */
  public boolean turnOff() {
    return isTvOn = false;
  }

  public int getNumberOfChannels() {
    if (!isTvOn){
      throw new IllegalStateException("TV must be turned on");
    }
    return numberOfChannels;
  }

  public int getCurrentChannel() {
    if (!isTvOn){
      throw new IllegalStateException("TV must be turned on");
    }
    return currentChannel;
  }

  /**
   * Set the channel for the TV
   * @param channel
   * @throws IllegalArgumentException
   */
  public void setChannel(int channel) throws IllegalArgumentException{
    //TODO
  }

  public boolean isTvOn() {
    return isTvOn;
  }
}