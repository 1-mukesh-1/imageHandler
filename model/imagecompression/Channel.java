package imagehandler.model.imagecompression;

/**
 * This class represents a channel of an image. This is dummy class for testing.
 */
public class Channel {
  private final double[][] original;
  private final double threshold;
  private final double[][] compressed;
  private final double[][] approximate;

  /**
   * This class represents a channel of an image. This is dummy class for testing.
   *
   * @param original      The original image.
   * @param threshold     The threshold used to compress the image.
   * @param compressed    The compressed image.
   * @param approximate   The approximate image.
   */
  public Channel(double[][] original, double threshold,
                 double[][] compressed, double[][] approximate) {
    this.original = original;
    this.threshold = threshold;
    this.compressed = compressed;
    this.approximate = approximate;
  }

  public double[][] getOriginal() {
    return original;
  }

  public double getThreshold() {
    return threshold;
  }

  public double[][] getCompressed() {
    return compressed;
  }

  public double[][] getApproximate() {
    return approximate;
  }
}