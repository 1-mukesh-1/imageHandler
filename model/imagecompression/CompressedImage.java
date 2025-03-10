package imagehandler.model.imagecompression;

/**
 * This class represents a compressed image.
 */
public class CompressedImage {
  private final Channel red;
  private final Channel green;
  private final Channel blue;

  /**
   * Constructor.
   * @param red The red channel of the compressed image.
   * @param green The green channel of the compressed image.
   * @param blue The blue channel of the compressed image.
   */
  public CompressedImage(Channel red, Channel green, Channel blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public Channel getRed() {
    return red;
  }

  public Channel getGreen() {
    return green;
  }

  public Channel getBlue() {
    return blue;
  }
}
