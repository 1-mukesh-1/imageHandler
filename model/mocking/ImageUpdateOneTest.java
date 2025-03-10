package imagehandler.model.mocking;

import imagehandler.model.IImageUpdateOne;
import imagehandler.model.IPixel;
import imagehandler.model.ImageUpdateOne;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class is used to test the ImageUpdateOne class.
 */
public class ImageUpdateOneTest {
  private IImageUpdateOne imageProcessor;
  private IPixel[][] testImage;

  @Before
  public void setUp() {
    imageProcessor = new ImageUpdateOne();
    testImage = new IPixel[][]{
        {new MockPixel(100, 150, 200), new MockPixel(120, 140, 160)},
        {new MockPixel(80, 90, 100), new MockPixel(200, 210, 220)}
    };
  }

  @Test
  public void testCompress() {
    imageProcessor.storeImage("test", testImage);
    imageProcessor.compress("test", "compressed", 50.0);
    IPixel[][] compressed = imageProcessor.getImage("compressed");
    assertNotNull(compressed);
    assertEquals(testImage.length, compressed.length);
    assertEquals(testImage[0].length, compressed[0].length);
  }

  @Test
  public void testLevelsAdjustment() {
    imageProcessor.storeImage("test", testImage);
    imageProcessor.levelsAdjustment("test", 10, 128, 250, "adjusted");
    IPixel[][] adjusted = imageProcessor.getImage("adjusted");
    assertNotNull(adjusted);
    assertEquals(testImage.length, adjusted.length);
    assertEquals(testImage[0].length, adjusted[0].length);
  }

  @Test
  public void testColorCorrection() {
    imageProcessor.storeImage("test", testImage);
    imageProcessor.colorCorrection("test", "corrected");
    IPixel[][] corrected = imageProcessor.getImage("corrected");
    assertNotNull(corrected);
    assertEquals(testImage.length, corrected.length);
    assertEquals(testImage[0].length, corrected[0].length);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCompressPercentage() {
    imageProcessor.storeImage("test", testImage);
    imageProcessor.compress("test", "compressed", 101.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidLevelsParameters() {
    imageProcessor.storeImage("test", testImage);
    imageProcessor.levelsAdjustment("test", 200, 100, 50, "adjusted");
  }
}