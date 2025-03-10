package imagehandler.controller.mocking;

import imagehandler.model.IImageUpdateOne;
import imagehandler.model.IPixel;
import imagehandler.model.Pixel;

import java.util.HashMap;
import java.util.Map;

/**
 * MockImageUpdateOne is a class that implements the IImageUpdateOne interface for
 * testing.
 */
public class MockImageUpdateOne implements IImageUpdateOne {
  public String lastLoadedFile;
  public String lastSavedFile;
  public String lastSourceImage;
  public String lastDestImage;
  public int lastBrightenIncrement;
  public String lastCommand;
  public boolean throwException;
  public int[] lastLevelsAdjustment;
  public double lastCompressionPercentage;
  public int lastSplitPercentage;
  public int[] lastDownscaleDimensions;
  private final Map<String, IPixel[][]> images;

  /**
   * Constructs a new MockImageUpdateOne object.
   */
  public MockImageUpdateOne() {
    this.images = new HashMap<>();
    this.lastLevelsAdjustment = new int[3];
    this.lastDownscaleDimensions = new int[2];
  }

  @Override
  public void blur(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "blur";
  }

  @Override
  public void sharpen(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "sharpen";
  }

  @Override
  public void brighten(int increment, String sourceName, String destName) {
    lastBrightenIncrement = increment;
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "brighten";
  }

  @Override
  public void flipHorizontally(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "horizontal-flip";
  }

  @Override
  public void flipVertically(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "vertical-flip";
  }

  @Override
  public void redComponent(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "red-component";
  }

  @Override
  public void greenComponent(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "green-component";
  }

  @Override
  public void blueComponent(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "blue-component";
  }

  @Override
  public void lumaGrayscale(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "luma-component";
  }

  @Override
  public void valueComponent(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "value-component";
  }

  @Override
  public void intensityComponent(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "intensity-component";
  }

  @Override
  public void applySepia(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "sepia";
  }

  @Override
  public void levelsAdjustment(String sourceName, int shadow, int mid, int highlight,
                               String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastLevelsAdjustment[0] = shadow;
    lastLevelsAdjustment[1] = mid;
    lastLevelsAdjustment[2] = highlight;
    lastCommand = "levels-adjust";
  }

  @Override
  public void compress(String sourceName, String destName, double percentage) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCompressionPercentage = percentage;
    lastCommand = "compress";
  }

  @Override
  public void colorCorrection(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "color-correct";
  }

  @Override
  public void generateHistogram(String sourceName, String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "histogram";
  }

  @Override
  public void downscaleImage(String sourceName, String destName, int targetWidth,
                             int targetHeight) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastDownscaleDimensions[0] = targetWidth;
    lastDownscaleDimensions[1] = targetHeight;
    lastCommand = "downscale";
  }

  @Override
  public void applyOperationPreview(String sourceName, String operation,
                                    String destName, int splitPercentage) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastSplitPercentage = splitPercentage;
    lastCommand = operation + "-preview";
  }

  @Override
  public IPixel[][] getImage(String imageName) {
    if (throwException) {
      throw new IllegalArgumentException("Image not found: " + imageName);
    }
    return new Pixel[1][1];  
  }

  @Override
  public void storeImage(String imageName, IPixel[][] image) {
    images.put(imageName, image);
  }

  @Override
  public void applyMaskedOperation(String operation, String sourceName, String maskName
      , String destName) {
    lastSourceImage = sourceName;
    lastDestImage = destName;
    lastCommand = "masked-" + operation;
  }

  @Override
  public IPixel createPixel(int red, int green, int blue) {
    return new Pixel(red, green, blue);
  }

  @Override
  public void rgbSplit(String imageName, String redImageName, String greenImageName,
                       String blueImageName) {
    lastSourceImage = imageName;
    lastDestImage = redImageName + "," + greenImageName + "," + blueImageName;
    lastCommand = "rgb-split";
  }

  @Override
  public void rgbCombine(String imageName, String redImageName, String greenImageName,
                         String blueImageName) {
    lastSourceImage = redImageName + "," + greenImageName + "," + blueImageName;
    lastDestImage = imageName;
    lastCommand = "rgb-combine";
  }
}
