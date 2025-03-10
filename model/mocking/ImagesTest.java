package imagehandler.model.mocking;

import imagehandler.model.IImages;
import imagehandler.model.IPixel;
import imagehandler.model.Images;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * This class is used to test the Images class.
 */
public class ImagesTest {
  private IImages images;
  private IPixel[][] testImage;

  @Before
  public void setUp() {
    images = new Images();
    testImage = new IPixel[][]{
        {new MockPixel(100, 150, 200), new MockPixel(120, 140, 160)},
        {new MockPixel(80, 90, 100), new MockPixel(200, 210, 220)}
    };
  }

  @Test
  public void testStoreAndGetImage() {
    images.storeImage("test", testImage);
    IPixel[][] retrieved = images.getImage("test");
    assertEquals(testImage.length, retrieved.length);
    assertEquals(testImage[0].length, retrieved[0].length);
    assertEquals(testImage[0][0].getRed(), retrieved[0][0].getRed());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNonexistentImage() {
    images.getImage("nonexistent");
  }

  @Test
  public void testFlipHorizontally() {
    images.storeImage("original", testImage);
    images.flipHorizontally("original", "flipped");
    IPixel[][] flipped = images.getImage("flipped");
    assertEquals(testImage[0][0].getRed(), flipped[0][1].getRed());
    assertEquals(testImage[0][1].getRed(), flipped[0][0].getRed());
  }

  @Test
  public void testFlipVertically() {
    images.storeImage("original", testImage);
    images.flipVertically("original", "flipped");
    IPixel[][] flipped = images.getImage("flipped");
    assertEquals(testImage[0][0].getRed(), flipped[1][0].getRed());
    assertEquals(testImage[1][0].getRed(), flipped[0][0].getRed());
  }

  @Test
  public void testBrighten() {
    images.storeImage("original", testImage);
    images.brighten(50, "original", "brightened");
    IPixel[][] brightened = images.getImage("brightened");
    assertEquals(150, brightened[0][0].getRed());
    assertEquals(200, brightened[0][0].getGreen());
    assertEquals(250, brightened[0][0].getBlue());
  }
}