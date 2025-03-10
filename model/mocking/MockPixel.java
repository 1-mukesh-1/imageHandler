package imagehandler.model.mocking;

import imagehandler.model.IPixel;

/**
 * This class implements the IPixel interface.
 * The MockPixel class is used for mocking.
 */
public class MockPixel implements IPixel {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * This is the MockPixel class constructor which is used for the
   * creation of the MockPixel object.
   *
   * @param red The red pixel value
   * @param green The green pixel value
   * @param blue The blue pixel value
   */
  public MockPixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getRed() {
    return red;
  }

  @Override
  public int getGreen() {
    return green;
  }

  @Override
  public int getBlue() {
    return blue;
  }
}