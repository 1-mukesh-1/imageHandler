package imagehandler.controller;

import imagehandler.model.IImageUpdateOne;
import imagehandler.model.ImageUpdateOne;
import imagehandler.view.IImageView;
import imagehandler.view.ImageView;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

/**
 * This class is used to test the ImageController class.
 */
public class ImageControllerScriptTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private ImageController controller;

  @Before
  public void setUp() {
    IImageUpdateOne model = new ImageUpdateOne();
    IImageView view = new ImageView();
    controller = new ImageController(model, view);

    File directory = new File("tempResult/");

    if (clearResults(directory)) {
      System.out.println("All Files cleared.");
    } else {
      System.out.println("Unable to clear all files.");
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

  @Test
  public void testRunningAllCommandsFromScript() {
    controller.handleCommand("run resources/all_commands.txt");

    String[] imageList = new String[]{
        "tempResult/baboon-sepia.png",
        "tempResult/baboon-blue.png",
        "tempResult/baboon-blur-2.png",
        "tempResult/baboon-blur.png",
        "tempResult/baboon-brighter-by-50.png",
        "tempResult/baboon-darker-by-50.png",
        "tempResult/baboon-green.png",
        "tempResult/baboon-horizontal-vertical.png",
        "tempResult/baboon-horizontal.png",
        "tempResult/baboon-intensity-greyscale.png",
        "tempResult/baboon-luma-greyscale.png",
        "tempResult/baboon-red.png",
        "tempResult/baboon-sharpen-2.png",
        "tempResult/baboon-sharper.png",
        "tempResult/baboon-value-greyscale.png",
        "tempResult/baboon-vertical-horizontal.png",
        "tempResult/baboon-vertical.png"
    };

    System.out.println(outContent);

    for (String imagePath : imageList) {
      assertTrue(new File(imagePath).exists());
    }
  }
}