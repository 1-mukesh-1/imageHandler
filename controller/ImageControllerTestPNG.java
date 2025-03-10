package imagehandler.controller;

/**
 * This class is used to test the
 * ImageController class with PNG Image as input.
 */
public class ImageControllerTestPNG extends AbstractImageControllerTest {

  protected String[] getControllerCommands() {
    return new String[]{
        "load resources/3x3_image.png 3x3-image",
        "load resources/baboon.png test-image"
    };
  }

  @Override
  protected String getFilePathThatIsSaved() {
    return "tempResult/test-image-new.png";
  }

  @Override
  protected String getSaveCommand() {
    return "save tempResult/test-image-new.png test-image";
  }
}