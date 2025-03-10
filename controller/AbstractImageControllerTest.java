package imagehandler.controller;

import imagehandler.model.IImageUpdateOne;
import imagehandler.model.ImageUpdateOne;
import imagehandler.view.IImageView;
import imagehandler.view.ImageView;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This is an abstract class that is used to test the ImageController class.
 */
public abstract class AbstractImageControllerTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private ImageController controller;
  private IImageUpdateOne model;

  @Before
  public void setUp() {
    model = new ImageUpdateOne();
    IImageView view = new ImageView();
    controller = new ImageController(model, view);

    File directory = new File("tempResult/");

    if (clearResults(directory)) {
      System.out.println("All Files cleared.");
    } else {
      System.out.println("Unable to clear all files.");
    }

    String[] commandList = getControllerCommands();
    for (String command : commandList) {
      controller.handleCommand(command);
    }

    System.setOut(new PrintStream(outContent));

  }

  private boolean clearResults(File directory) {
    if (!directory.exists() || !directory.isDirectory()) {
      System.out.println("Provided path is not a valid directory.");
      return false;
    }

    File[] files = directory.listFiles();
    if (files == null) {
      return false;
    }

    for (File file : files) {
      if (file.isFile() && !file.delete()) {
        System.out.println("Failed to delete file: " + file.getName());
        return false;
      }
    }
    return true;
  }

  protected abstract String[] getControllerCommands();

  @Test
  public void testScriptFileReading() {
    controller.handleCommand("run resources/test_run_command.txt");

    String[] imageList = new String[]{
        "tempResult/run-baboon-sepia.png",
        "tempResult/run-baboon-red.png",
        "tempResult/run-baboon-green.png",
        "tempResult/run-baboon-blue.png",
        "tempResult/run-baboon-blur.png",
        "tempResult/run-baboon-combined.png"
    };

    System.out.println(outContent);

    for (String imagePath : imageList) {
      assertTrue(new File(imagePath).exists());
    }
  }

  @Test
  public void testScriptFileParsing() {
    controller.handleCommand("run resources/test_run_command.txt");

    String[] imageList = new String[]{
        "tempResult/run-baboon-sepia.png",
        "tempResult/run-baboon-red.png",
        "tempResult/run-baboon-green.png",
        "tempResult/run-baboon-blue.png",
        "tempResult/run-baboon-blur.png",
        "tempResult/run-baboon-combined.png"
    };

    System.out.println(outContent);

    for (String imagePath : imageList) {
      assertTrue(new File(imagePath).exists());
    }
  }

  @Test
  public void testErrorInScriptFile() {
    controller.handleCommand("run resources/wrong_commands.txt");
    assertTrue(outContent.toString().contains("Unsupported command"));
  }

  @Test
  public void testProcessCommandFile() {
    try {
      String commandFile = "resources/commands.txt";
      List<String> commands = Files.readAllLines(Paths.get(commandFile));
      for (String command : commands) {
        controller.handleCommand(command);

      }

      String[] imageList = new String[]{
          "tempResult/baboon-sepia.png",
          "tempResult/baboon-green.png",
          "tempResult/baboon-blur.png",
          "tempResult/baboon-combined.png",
          "tempResult/baboon-blue.png",
          "tempResult/baboon-red.png"
      };

      for (String imagePath : imageList) {
        assertTrue(new File(imagePath).exists());
      }
    } catch (IOException e) {
      fail("Failed to process command file: " + e.getMessage());
    }
  }

  @Test
  public void testInvalidCommands() {
    controller.handleCommand("invalid-command example test");
    assertTrue(outContent.toString().contains("Unsupported command"));
  }

  @Test
  public void testLoadImage() {
    assertNotNull(model.getImage("test-image"));
  }

  @Test
  public void testHandleCommandLoad() {
    assertNotNull(model.getImage("test-image"));
  }

  @Test
  public void testHandleCommandBrighten() {
    controller.handleCommand("brighten 30 test-image test-bright");
    assertNotNull(model.getImage("test-bright"));
  }

  @Test
  public void testHandleCommandHorizontalFlip() {
    controller.handleCommand("horizontal-flip test-image test-horizontal-flip");
    assertNotNull(model.getImage("test-horizontal-flip"));
  }

  @Test
  public void testHandleCommandValueGreyScale() {
    controller.handleCommand("value-component test-image test-value-grey-scale");
    assertNotNull(model.getImage("test-value-grey-scale"));
  }

  @Test
  public void testHandleCommandIntensityGreyScale() {
    controller.handleCommand("intensity-component test-image test-intensity-grey-scale");
    assertNotNull(model.getImage("test-intensity-grey-scale"));
  }

  @Test
  public void testHandleCommandLumaGreyScale() {
    controller.handleCommand("luma-component test-image test-luma-grey-scale");
    assertNotNull(model.getImage("test-luma-grey-scale"));
  }

  @Test
  public void testHandleCommandSepia() {
    controller.handleCommand("sepia test-image test-sepia");
    assertNotNull(model.getImage("test-sepia"));
  }

  @Test
  public void testRGBOperations() {
    controller.handleCommand("rgb-split test-image red-comp green-comp blue-comp");
    assertNotNull(model.getImage("red-comp"));
    assertNotNull(model.getImage("green-comp"));
    assertNotNull(model.getImage("blue-comp"));
    controller.handleCommand("rgb-combine combined red-comp green-comp blue-comp");
    assertNotNull(model.getImage("combined"));
  }

  @Test
  public void testHandleCommandGreenComponent() {
    controller.handleCommand("green-component test-image test-green");
    assertNotNull(model.getImage("test-green"));
  }

  @Test
  public void testHandleCommandBlueComponent() {
    controller.handleCommand("blue-component test-image test-blue");
    assertNotNull(model.getImage("test-blue"));
  }

  @Test
  public void testHandleCommandRedComponent() {
    controller.handleCommand("red-component test-image test-red");
    assertNotNull(model.getImage("test-red"));
  }

  @Test
  public void testHandleCommandVerticalFlip() {
    controller.handleCommand("vertical-flip test-image test-vertical-flip");
    assertNotNull(model.getImage("test-vertical-flip"));
  }

  @Test
  public void testHandleCommandBlur() {
    controller.handleCommand("blur test-image test-blurred");
    assertNotNull(model.getImage("test-blurred"));
  }

  @Test
  public void testHandleCommandSharpen() {
    controller.handleCommand("sharpen test-image test-sharpened");
    assertNotNull(model.getImage("test-sharpened"));
  }

  @Test
  public void testHandleCommandRgbCombine() {
    controller.handleCommand("rgb-split test-image test-red test-green test-blue");
    controller.handleCommand("rgb-combine combined test-red test-green test-blue");
    assertNotNull(model.getImage("combined"));
  }

  @Test
  public void testCommandCompress() {
    controller.handleCommand("compress 50 test-image test-compressed");
    assertNotNull(model.getImage("test-compressed"));
  }

  @Test
  public void testRunCommand() {
    controller.handleCommand("run resources/test_run_command.txt");

    String[] imageList = new String[]{
        "tempResult/run-baboon-sepia.png",
        "tempResult/run-baboon-red.png",
        "tempResult/run-baboon-green.png",
        "tempResult/run-baboon-blue.png",
        "tempResult/run-baboon-blur.png",
        "tempResult/run-baboon-combined.png"
    };

    System.out.println(outContent);

    for (String imagePath : imageList) {
      assertTrue(new File(imagePath).exists());
    }
  }

  @Test
  public void testSaveImage() {
    controller.handleCommand(getSaveCommand());
    assertTrue(new File(getFilePathThatIsSaved()).exists());
  }

  @Test
  public void testHandleCommandCompress() {
    controller.handleCommand("compress 50 test-image test-compressed");
    assertNotNull(model.getImage("test-compressed"));
  }

  @Test
  public void testHandleCommandColorCorrect() {
    controller.handleCommand("color-correct test-image test-color-corrected");
    assertNotNull(model.getImage("test-color-corrected"));
  }

  @Test
  public void testHandleCommandHistogram() {
    controller.handleCommand("histogram test-image test-histogram");
    assertNotNull(model.getImage("test-histogram"));
  }

  @Test
  public void testHandleCommandSplitBlur() {
    controller.handleCommand("blur test-image test-blur-preview split 40");
    assertNotNull(model.getImage("test-blur-preview"));
  }

  @Test
  public void testHandleCommandSplitSharpen() {
    controller.handleCommand("sharpen test-image test-sharpen-preview split 50");
    assertNotNull(model.getImage("test-sharpen-preview"));
  }

  @Test
  public void testHandleCommandSplitSepia() {
    controller.handleCommand("sepia test-image test-sepia-preview split 50");
    assertNotNull(model.getImage("test-sepia-preview"));
  }

  @Test
  public void testHandleCommandSplitBrighten() {
    controller.handleCommand("brighten 50 test-image test-brighten-preview split 70");
    assertNotNull(model.getImage("test-brighten-preview"));
  }

  @Test
  public void testHandleCommandSplitDefaultPercentage() {
    controller.handleCommand("blur test-image test-blur-default split");
    assertNotNull(model.getImage("test-blur-default"));
  }

  @Test
  public void testHandleCommandMultipleOperationsWithSplit() {
    controller.handleCommand("blur test-image test-blur-split split 50");
    assertNotNull(model.getImage("test-blur-split"));
    controller.handleCommand("sharpen test-blur-split test-blur-sharp-split split 30");
    assertNotNull(model.getImage("test-blur-sharp-split"));

  }

  @Test
  public void testHandleCommandSplitColorCorrect() {
    controller.handleCommand("color-correct test-image test-color-preview split 40");
    assertNotNull(model.getImage("test-color-preview"));
  }

  @Test
  public void testHandleCommandSplitLevelsAdjust() {
    controller.handleCommand(
        "levels-adjust 20 128 200 test-image test-levels-preview split 60");
    assertNotNull(model.getImage("test-levels-preview"));
  }

  protected abstract String getFilePathThatIsSaved();

  protected abstract String getSaveCommand();
}