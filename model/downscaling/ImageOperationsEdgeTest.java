package imagehandler.model.downscaling;

import imagehandler.model.IImageUpdateOne;
import imagehandler.model.IPixel;
import imagehandler.model.ImageUpdateOne;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is used to test the ImageOperations class.
 */
public class ImageOperationsEdgeTest {
  private IImageUpdateOne model;
  private static final String SOURCE_IMAGE = "source";
  private static final String DEST_IMAGE = "dest";
  private static final String MASK_IMAGE = "mask";

  @Before
  public void setUp() {
    model = new ImageUpdateOne();
    setupTestImages();
  }

  private void setupTestImages() {
    
    IPixel[][] sourceImage = new IPixel[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        sourceImage[i][j] = model.createPixel(i * 50, j * 50, (i + j) * 30);
      }
    }
    model.storeImage(SOURCE_IMAGE, sourceImage);

    IPixel[][] singlePixel = new IPixel[][]{{model.createPixel(100, 100, 100)}};
    model.storeImage("single_pixel", singlePixel);

    IPixel[][] rectImage = new IPixel[2][4];
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 4; j++) {
        rectImage[i][j] = model.createPixel(i * 50, j * 50, (i + j) * 30);
      }
    }
    model.storeImage("rect_image", rectImage);

    setupMaskPatterns();
  }

  private void setupMaskPatterns() {
    
    IPixel[][] maskImage = new IPixel[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        int color = j < 2 ? 0 : 255;
        maskImage[i][j] = model.createPixel(color, color, color);
      }
    }
    model.storeImage(MASK_IMAGE, maskImage);

    IPixel[][] checkerboard = new IPixel[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        int color = (i + j) % 2 == 0 ? 0 : 255;
        checkerboard[i][j] = model.createPixel(color, color, color);
      }
    }
    model.storeImage("checker_mask", checkerboard);

    IPixel[][] gradient = new IPixel[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        int color = (i + j) * 32;
        gradient[i][j] = model.createPixel(color, color, color);
      }
    }
    model.storeImage("gradient_mask", gradient);
  }

  @Test
  public void testDownscaleSinglePixel() {
    model.downscaleImage("single_pixel", DEST_IMAGE, 1, 1);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    assertEquals(1, result.length);
    assertEquals(1, result[0].length);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleToZeroWidth() {
    model.downscaleImage(SOURCE_IMAGE, DEST_IMAGE, 0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleToZeroHeight() {
    model.downscaleImage(SOURCE_IMAGE, DEST_IMAGE, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDownscaleToNegativeDimensions() {
    model.downscaleImage(SOURCE_IMAGE, DEST_IMAGE, -2, -2);
  }

  @Test
  public void testDownscaleRectangularImage() {
    model.downscaleImage("rect_image", DEST_IMAGE, 2, 1);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    assertEquals(1, result.length);
    assertEquals(2, result[0].length);
  }

  @Test
  public void testDownscaleToExtremelySmallSize() {
    model.downscaleImage(SOURCE_IMAGE, DEST_IMAGE, 1, 1);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    assertEquals(1, result.length);
    assertEquals(1, result[0].length);
  }

  @Test
  public void testDownscalePreservesRGBRange() {
    model.downscaleImage(SOURCE_IMAGE, DEST_IMAGE, 2, 2);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    for (IPixel[] row : result) {
      for (IPixel pixel : row) {
        assertTrue(pixel.getRed() >= 0 && pixel.getRed() <= 255);
        assertTrue(pixel.getGreen() >= 0 && pixel.getGreen() <= 255);
        assertTrue(pixel.getBlue() >= 0 && pixel.getBlue() <= 255);
      }
    }
  }

  @Test
  public void testDownscaleWithOddDimensions() {
    
    IPixel[][] oddImage = new IPixel[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        oddImage[i][j] = model.createPixel(i * 50, j * 50, (i + j) * 30);
      }
    }
    model.storeImage("odd_image", oddImage);
    model.downscaleImage("odd_image", DEST_IMAGE, 2, 2);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    assertEquals(2, result.length);
    assertEquals(2, result[0].length);
  }

  @Test
  public void testMaskWithSinglePixelImage() {
    IPixel[][] singlePixelMask = new IPixel[][]{{model.createPixel(0, 0, 0)}};
    model.storeImage("single_mask", singlePixelMask);
    model.applyMaskedOperation("blur", "single_pixel", "single_mask", DEST_IMAGE);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    assertEquals(1, result.length);
    assertEquals(1, result[0].length);
  }

  @Test
  public void testMaskWithAllBlack() {
    IPixel[][] blackMask = new IPixel[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        blackMask[i][j] = model.createPixel(0, 0, 0);
      }
    }
    model.storeImage("black_mask", blackMask);
    model.applyMaskedOperation("sepia", SOURCE_IMAGE, "black_mask", DEST_IMAGE);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    IPixel[][] original = model.getImage(SOURCE_IMAGE);
    assertNotEquals(original[1][1].getRed(), result[1][1].getRed());
  }

  @Test
  public void testMaskWithAllWhite() {
    IPixel[][] whiteMask = new IPixel[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        whiteMask[i][j] = model.createPixel(255, 255, 255);
      }
    }
    model.storeImage("white_mask", whiteMask);
    model.applyMaskedOperation("blur", SOURCE_IMAGE, "white_mask", DEST_IMAGE);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    IPixel[][] original = model.getImage(SOURCE_IMAGE);
    assertArrayEquals(original, result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColouredMask() {
    model.applyMaskedOperation("blur", SOURCE_IMAGE, "gradient_mask", DEST_IMAGE);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    IPixel[][] original = model.getImage(SOURCE_IMAGE);

    boolean foundDifference = false;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (!pixelsEqual(original[i][j], result[i][j])) {
          foundDifference = true;
          break;
        }
      }
    }
    assertTrue("Gradient mask should cause some pixels to change", foundDifference);
  }

  @Test
  public void testMaskWithCheckerboard() {
    model.applyMaskedOperation("blur", SOURCE_IMAGE, "checker_mask", DEST_IMAGE);
    IPixel[][] result = model.getImage(DEST_IMAGE);
    IPixel[][] original = model.getImage(SOURCE_IMAGE);

    boolean foundAlternating = false;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        boolean currentEqual = pixelsEqual(original[i][j], result[i][j]);
        boolean nextEqual = pixelsEqual(original[i][j + 1], result[i][j + 1]);
        if (currentEqual != nextEqual) {
          foundAlternating = true;
          break;
        }
      }
    }
    assertTrue("Checkerboard mask should create alternating pattern", foundAlternating);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMaskWithNonGrayscale() {
    IPixel[][] colorMask = new IPixel[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        colorMask[i][j] = model.createPixel(255, 0, 0); 
      }
    }
    model.storeImage("color_mask", colorMask);
    model.applyMaskedOperation("blur", SOURCE_IMAGE, "color_mask", DEST_IMAGE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMaskWithNull() {
    model.applyMaskedOperation("blur", SOURCE_IMAGE, null, DEST_IMAGE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMaskWithNonexistentImage() {
    model.applyMaskedOperation("blur", SOURCE_IMAGE, "nonexistent_mask", DEST_IMAGE);
  }

  private boolean pixelsEqual(IPixel p1, IPixel p2) {
    return p1.getRed() == p2.getRed() &&
        p1.getGreen() == p2.getGreen() &&
        p1.getBlue() == p2.getBlue();
  }
}