package imagehandler.controller;

/**
 * This class is used to test the
 * ImageController class with PPM Image as input.
 */
public class ImageControllerTestPPM extends AbstractImageControllerTest {

  protected String[] getControllerCommands() {
    return new String[]{
        "load resources/3x3_image.png 3x3-image",
        "load resources/random_colors.ppm test-image"
    };
  }

  @Override
  protected String getFilePathThatIsSaved() {
    return "tempResult/test-image-new.ppm";
  }

  @Override
  protected String getSaveCommand() {
    return "save tempResult/test-image-new.ppm test-image";
  }
}