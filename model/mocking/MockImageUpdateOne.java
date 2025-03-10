package imagehandler.model.mocking;

import imagehandler.model.IImageUpdateOne;
import imagehandler.model.IPixel;
import imagehandler.model.Images;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the IImageUpdateOne interface for image processing and manipulation.
 * Handles multiple images and their transformations. Each image is stored in a hashmap
 * with the image name as the key and the pixel matrix as the value. Provides methods
 * for image transformations such as blurring, sharpening, grey-scaling, sepia and
 * flipping the image horizontally and vertically. Also provides methods
 * for combining and splitting RGB images.
 */
public class MockImageUpdateOne extends Images implements IImageUpdateOne {
  private final Map<String, IPixel[][]> images = new HashMap<>();
  private String lastMessage = null;

  public String getLastMessage() {
    return lastMessage;
  }

  public void setLastMessage(String message) {
    this.lastMessage = message;
  }

  @Override
  public void storeImage(String imageName, IPixel[][] pixels) {
    images.put(imageName, pixels);
  }

  @Override
  public IPixel[][] getImage(String imageName) {
    if (!images.containsKey(imageName)) {
      throw new IllegalArgumentException("Image not found: " + imageName);
    }
    return images.get(imageName);
  }

  @Override
  public void blur(String imageName, String destName) {
    setLastMessage("blurred: " + imageName);
  }

  @Override
  public void brighten(int increment, String imageName, String destName) {
    setLastMessage("brightened: " + imageName + " by " + increment);
  }

  @Override
  public void rgbSplit(String imageName, String redDest, String greenDest,
                       String blueDest) {
    setLastMessage("RGB split applied: " + imageName);
  }

  @Override
  public void compress(String imageName, String destImageName, double percentage) {
    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Percentage must be between 0 and 100");
    }
    setLastMessage("compressed: " + imageName);
  }

  @Override
  public void levelsAdjustment(String imageName, int shadow,
                               int mid, int highlight, String newImageName) {
    setLastMessage("levels adjusted: " + imageName);
  }

  @Override
  public void colorCorrection(String imageName, String newImageName) {
    setLastMessage("color corrected: " + imageName);
  }

  @Override
  public void generateHistogram(String imageName, String destImageName) {
    setLastMessage("histogram generated: " + imageName);
  }

  @Override
  public void applyOperationPreview(String imageName, String operation,
                                    String destImageName, int splitPercentage) {
    setLastMessage("operation preview applied: " + operation + " on " + imageName);
  }

  @Override
  public void downscaleImage(String sourceImage, String destImage, int targetWidth,
                             int targetHeight) {
    setLastMessage("downscaled: " + sourceImage);
  }

  @Override
  public void applyMaskedOperation(String operation, String sourceName, String maskName
      , String destName) throws IllegalArgumentException {
    setLastMessage("masked " + operation + " applied: " + sourceName);
  }

  public boolean hasImage(String imageName) {
    return images.containsKey(imageName);
  }
}