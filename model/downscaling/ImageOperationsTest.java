package imagehandler.model.downscaling;

import imagehandler.model.IImageUpdateOne;
import imagehandler.model.IPixel;
import imagehandler.model.ImageUpdateOne;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is used to test the ImageOperations class.
 */
public class ImageOperationsTest {
  private IImageUpdateOne model;
  private static final String SOURCE_IMAGE = "source";
  private static final String DEST_IMAGE = "dest";
  private static final String MASK_IMAGE = "mask";

  @Before
  public void setUp() {
    model = new ImageUpdateOne();
    
    IPixel[][] sourceImage = new IPixel[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        sourceImage[i][j] = model.createPixel(i * 50, j * 50, (i + j) * 30);
      }
    }
    model.storeImage(SOURCE_IMAGE, sourceImage);

    IPixel[][] maskImage = new IPixel[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        int color = j < 2 ? 0 : 255;  
        maskImage[i][j] = model.createPixel(color, color, color);
      }
    }
    model.storeImage(MASK_IMAGE, maskImage);
  }

  @Test
  public void testDownscaleToHalfSize() {
    model.downscaleImage(SOURCE_IMAGE, DEST_IMAGE, 2, 2);
    IPixel[][] result = model.getImage(DEST_IMAGE);

    assertEquals(2, result.length);
    assertEquals(2, result[0].length);

    IPixel topLeft = result[0][0];
    assertTrue(topLeft.getRed() >= 0 && topLeft.getRed() <= 50);
    assertTrue(topLeft.getGreen() >= 0 && topLeft.getGreen() <= 50);
    assertTrue(topLeft.getBlue() >= 0 && topLeft.getBlue() <= 30);
  }

  @Test
  public void testDownscaleToSameSize() {
    model.downscaleImage(SOURCE_IMAGE, DEST_IMAGE, 4, 4);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    IPixel[][] original = model.getImage(SOURCE_IMAGE);

    assertEquals(original.length, result.length);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleToLargerSize() {
    model.downscaleImage(SOURCE_IMAGE, DEST_IMAGE, 8, 8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleWithZeroDimension() {
    model.downscaleImage(SOURCE_IMAGE, DEST_IMAGE, 0, 4);
  }

  @Test
  public void testBlurWithMask() {
    model.applyMaskedOperation("sepia", SOURCE_IMAGE, MASK_IMAGE, DEST_IMAGE);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    IPixel[][] original = model.getImage(SOURCE_IMAGE);
    assertNotEquals(original[1][1].getRed(), result[1][1].getRed());
    assertEquals(original[1][3].getRed(), result[1][3].getRed());
    assertEquals(original[1][3].getGreen(), result[1][3].getGreen());
    assertEquals(original[1][3].getBlue(), result[1][3].getBlue());
  }

  @Test
  public void testSepiaWithMask() {
    model.applyMaskedOperation("sepia", SOURCE_IMAGE, MASK_IMAGE, DEST_IMAGE);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    IPixel[][] original = model.getImage(SOURCE_IMAGE);

    assertTrue(result[1][0].getRed() != result[1][0].getGreen());
    assertTrue(result[1][0].getGreen() != result[1][0].getBlue());

    assertEquals(original[1][3].getRed(), result[1][3].getRed());
    assertEquals(original[1][3].getGreen(), result[1][3].getGreen());
    assertEquals(original[1][3].getBlue(), result[1][3].getBlue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMaskWithDifferentDimensions() {
    IPixel[][] smallMask = new IPixel[2][2];
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        smallMask[i][j] = model.createPixel(0, 0, 0);
      }
    }
    model.storeImage("small_mask", smallMask);
    model.applyMaskedOperation("blur", SOURCE_IMAGE, "small_mask", DEST_IMAGE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMaskWithInvalidOperation() {
    model.applyMaskedOperation("invalid_op", SOURCE_IMAGE, MASK_IMAGE, DEST_IMAGE);
  }

  @Test
  public void testMultipleOperationsWithMask() {
    
    model.applyMaskedOperation("blur", SOURCE_IMAGE, MASK_IMAGE, "temp");
    model.applyMaskedOperation("sepia", "temp", MASK_IMAGE, DEST_IMAGE);

    IPixel[][] result = model.getImage(DEST_IMAGE);
    IPixel[][] original = model.getImage(SOURCE_IMAGE);

    assertTrue(result[1][0].getRed() > result[1][0].getGreen());
    assertTrue(result[1][0].getGreen() > result[1][0].getBlue());
    assertNotEquals(original[1][0].getRed(), result[1][0].getRed());

    assertEquals(original[1][3].getRed(), result[1][3].getRed());
    assertEquals(original[1][3].getGreen(), result[1][3].getGreen());
    assertEquals(original[1][3].getBlue(), result[1][3].getBlue());
  }
}