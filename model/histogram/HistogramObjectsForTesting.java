package imagehandler.model.histogram;

/**
 * This class contains objects for testing.
 */
public class HistogramObjectsForTesting {
  /**
   * This class contains objects for testing.
   */
  public static class TwoCrossTwoImage {
    public static final int HEIGHT = 2;
    public static final int WIDTH = 2;

    public static final int SHADOW = 12;
    public static final int MID = 115;
    public static final int HIGHLIGHT = 206;

    public static final int[][][] IMAGE = {
        {
            {140, 144, 56}, {40, 227, 4}
        },
        {
            {72, 232, 93}, {68, 169, 84}
        }
    };

    public static final int[][][] COLOR_CORRECTED = {
        {
            {180, 80, 80}, {80, 163, 28}
        },
        {
            {112, 168, 117}, {108, 105, 108}
        }
    };

    public static final double[][][] LEVELS_ADJUSTED = {
        {
            {161, 167, 52}, {33, 255, 0}
        },
        {
            {72, 255, 99}, {67, 201, 87}
        }
    };
  }
}