package imagehandler.model.imagecompression;

import imagehandler.controller.ImageController;
import imagehandler.model.IImageUpdateOne;
import imagehandler.model.IPixel;
import imagehandler.model.ImageUpdateOne;
import imagehandler.model.Pixel;
import imagehandler.view.IImageView;
import imagehandler.view.ImageView;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Math.abs;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This class is used to test the ImageCompressionSmallImages class.
 */
public class ImageCompressionSmallImages {
  private IImageUpdateOne model;

  private boolean compareCompressionObjects(CompressedImage img, IPixel[][] compressed) {
    for (int i = 0; i < img.getRed().getApproximate().length; i++) {
      for (int j = 0; j < img.getRed().getApproximate()[0].length; j++) {
        if (abs(img.getRed().getOriginal()[i][j] - compressed[i][j].getRed()) > 1
            || abs(img.getGreen().getOriginal()[i][j] - compressed[i][j].getGreen()) > 1
            || abs(img.getBlue().getOriginal()[i][j] - compressed[i][j].getBlue()) > 1) {
          return false;
        }
      }
    }

    for (int i = 0; i < img.getRed().getApproximate().length; i++) {
      for (int j = 0; j < img.getRed().getApproximate()[0].length; j++) {
        System.out.print("(" + img.getRed().getOriginal()[i][j] + " "
            + img.getGreen().getOriginal()[i][j] + " "
            + img.getBlue().getOriginal()[i][j] + ") ");
      }
      System.out.println();
    }
    for (int i = 0; i < img.getRed().getApproximate().length; i++) {
      for (int j = 0; j < img.getRed().getApproximate()[0].length; j++) {
        System.out.print("[(" + img.getRed().getCompressed()[i][j]
            + "," + img.getGreen().getApproximate()[i][j]
            + "," + img.getBlue().getApproximate()[i][j]
            + ") # " + "(" + compressed[i][j].getRed()
            + "," + compressed[i][j].getGreen()
            + "," + compressed[i][j].getBlue()
            + ")] ");
      }
      System.out.println();
    }
    return true;
  }

  @Before
  public void setUp() {
    model = new ImageUpdateOne();
    IImageView view = new ImageView();
    ImageController controller = new ImageController(model, view);
  }

  @Test
  public void testTwoCrossTwoCompression() {
    CompressedImage two_cross_two =
        CompressionObjectsForTesting.TWO_CROSS_TWO_NO_PADDING;
    IPixel[][] image =
        new Pixel[two_cross_two.getRed().getOriginal().length]
            [two_cross_two.getRed().getOriginal()[0].length];
    for (int i = 0; i < two_cross_two.getRed().getOriginal().length; i++) {
      for (int j = 0; j < two_cross_two.getRed().getOriginal()[0].length; j++) {
        image[i][j] = new Pixel((int) two_cross_two.getRed().getOriginal()[i][j],
            (int) two_cross_two.getGreen().getOriginal()[i][j],
            (int) two_cross_two.getBlue().getOriginal()[i][j]);
      }
    }
    model.storeImage("small-image", image);
    model.compress("small-image", "test-compressed",
        (int) CompressionObjectsForTesting.TWO_CROSS_TWO_NO_PADDING.getRed().getThreshold());
    assertTrue(compareCompressionObjects(CompressionObjectsForTesting.TWO_CROSS_TWO_NO_PADDING,
        model.getImage("test-compressed")));
  }

  @Test
  public void testThreeCrossThreeCompression() {
    CompressedImage three_cross_three =
        CompressionObjectsForTesting.THREE_CROSS_THREE_PADDING;
    IPixel[][] image =
        new Pixel[three_cross_three.getRed().getOriginal().length]
            [three_cross_three.getRed().getOriginal()[0].length];
    for (int i = 0; i < three_cross_three.getRed().getOriginal().length; i++) {
      for (int j = 0; j < three_cross_three.getRed().getOriginal()[0].length; j++) {
        image[i][j] = new Pixel((int) three_cross_three.getRed().getOriginal()[i][j],
            (int) three_cross_three.getGreen().getOriginal()[i][j],
            (int) three_cross_three.getBlue().getOriginal()[i][j]);
      }
    }
    model.storeImage("small-image", image);
    model.compress("small-image", "test-compressed",
        (int) CompressionObjectsForTesting.THREE_CROSS_THREE_PADDING.getRed().getThreshold());
    assertNotNull(model.getImage("test-compressed"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void compressionInvalidInputG100() {
    CompressedImage two_cross_two =
        CompressionObjectsForTesting.TWO_CROSS_TWO_NO_PADDING;
    IPixel[][] image =
        new Pixel[two_cross_two.getRed().getOriginal().length]
                [two_cross_two.getRed().getOriginal()[0].length];
    for (int i = 0; i < two_cross_two.getRed().getOriginal().length; i++) {
      for (int j = 0; j < two_cross_two.getRed().getOriginal()[0].length; j++) {
        image[i][j] = new Pixel((int) two_cross_two.getRed().getOriginal()[i][j],
            (int) two_cross_two.getGreen().getOriginal()[i][j],
            (int) two_cross_two.getBlue().getOriginal()[i][j]);
      }
    }
    model.storeImage("small-image", image);
    model.compress("small-image", "test-compressed",
        110);
    compareCompressionObjects(CompressionObjectsForTesting.TWO_CROSS_TWO_NO_PADDING,
        model.getImage("test-compressed"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void compressionInvalidInputL0() {
    CompressedImage two_cross_two =
        CompressionObjectsForTesting.TWO_CROSS_TWO_NO_PADDING;
    IPixel[][] image =
        new Pixel[two_cross_two.getRed().getOriginal().length]
                [two_cross_two.getRed().getOriginal()[0].length];
    for (int i = 0; i < two_cross_two.getRed().getOriginal().length; i++) {
      for (int j = 0; j < two_cross_two.getRed().getOriginal()[0].length; j++) {
        image[i][j] = new Pixel((int) two_cross_two.getRed().getOriginal()[i][j],
            (int) two_cross_two.getGreen().getOriginal()[i][j],
            (int) two_cross_two.getBlue().getOriginal()[i][j]);
      }
    }
    model.storeImage("small-image", image);
    model.compress("small-image", "test-compressed",
        -10);
    compareCompressionObjects(CompressionObjectsForTesting.TWO_CROSS_TWO_NO_PADDING,
        model.getImage("test-compressed"));
  }
}