package imagehandler.controller;

import imagehandler.controller.mocking.MockImageCommandFactory;
import imagehandler.controller.mocking.MockImageUpdateOne;
import imagehandler.controller.mocking.MockImageView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is used to test the ImageController class.
 */
public class MockImageControllerTest {
  private MockImageUpdateOne mockModel;
  private MockImageView mockView;
  private ImageController controller;

  @Before
  public void setup() {
    mockModel = new MockImageUpdateOne();
    mockView = new MockImageView();
    MockImageCommandFactory mockFactory = new MockImageCommandFactory(mockModel,
        mockView);
    controller = new ImageController(mockModel, mockView);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  @After
  public void tearDown() {
    System.setOut(System.out);
  }

  @Test
  public void testMaskedOperationUsesFactory() {
    controller.handleCommand("sepia source mask dest");
    assertEquals("masked-sepia", mockModel.lastCommand);
  }

  @Test
  public void testLoadImageWithError() {
    mockModel.throwException = true;
    controller.handleCommand("load test.jpg test");
    assertTrue(mockView.getLastMessage().contains("Error"));
  }

  @Test
  public void testBlurCommand() {
    controller.handleCommand("blur source dest");
    assertEquals("source", mockModel.lastSourceImage);
    assertEquals("dest", mockModel.lastDestImage);
    assertEquals("blur", mockModel.lastCommand);
  }

  @Test
  public void testSharpenCommand() {
    controller.handleCommand("sharpen source dest");
    assertEquals("sharpen", mockModel.lastCommand);
  }

  @Test
  public void testBrightenCommand() {
    controller.handleCommand("brighten 50 source dest");
    assertEquals(50, mockModel.lastBrightenIncrement);
    assertEquals("brighten", mockModel.lastCommand);
  }

  @Test
  public void testHorizontalFlipCommand() {
    controller.handleCommand("horizontal-flip source dest");
    assertEquals("horizontal-flip", mockModel.lastCommand);
  }

  @Test
  public void testVerticalFlipCommand() {
    controller.handleCommand("vertical-flip source dest");
    assertEquals("vertical-flip", mockModel.lastCommand);
  }

  @Test
  public void testRedComponentCommand() {
    controller.handleCommand("red-component source dest");
    assertEquals("red-component", mockModel.lastCommand);
  }

  @Test
  public void testGreenComponentCommand() {
    controller.handleCommand("green-component source dest");
    assertEquals("green-component", mockModel.lastCommand);
  }

  @Test
  public void testBlueComponentCommand() {
    controller.handleCommand("blue-component source dest");
    assertEquals("blue-component", mockModel.lastCommand);
  }

  @Test
  public void testLumaComponentCommand() {
    controller.handleCommand("luma-component source dest");
    assertEquals("luma-component", mockModel.lastCommand);
  }

  @Test
  public void testValueComponentCommand() {
    controller.handleCommand("value-component source dest");
    assertEquals("value-component", mockModel.lastCommand);
  }

  @Test
  public void testIntensityComponentCommand() {
    controller.handleCommand("intensity-component source dest");
    assertEquals("intensity-component", mockModel.lastCommand);
  }

  @Test
  public void testSepiaCommand() {
    controller.handleCommand("sepia source dest");
    assertEquals("sepia", mockModel.lastCommand);
  }

  @Test
  public void testLevelsAdjustCommand() {
    controller.handleCommand("levels-adjust 10 150 220 source dest");
    assertEquals("levels-adjust", mockModel.lastCommand);
    assertArrayEquals(new int[]{10, 150, 220}, mockModel.lastLevelsAdjustment);
  }

  @Test
  public void testCompressCommand() {
    controller.handleCommand("compress 20 source dest");
    assertEquals("compress", mockModel.lastCommand);
    assertEquals(20.0, mockModel.lastCompressionPercentage, 0.001);
  }

  @Test
  public void testColorCorrectionCommand() {
    controller.handleCommand("color-correct source dest");
    assertEquals("color-correct", mockModel.lastCommand);
  }

  @Test
  public void testHistogramCommand() {
    controller.handleCommand("histogram source dest");
    assertEquals("histogram", mockModel.lastCommand);
  }

  @Test
  public void testDownscaleCommand() {
    controller.handleCommand("downscale 100 100 source dest");
    assertEquals("downscale", mockModel.lastCommand);
    assertArrayEquals(new int[]{100, 100}, mockModel.lastDownscaleDimensions);
  }

  @Test
  public void testPreviewCommand() {
    controller.handleCommand("sepia source dest split 50");
    assertEquals("sepia-preview", mockModel.lastCommand);
    assertEquals(50, mockModel.lastSplitPercentage);
  }

  @Test
  public void testExecuteProgramWithExit() {
    ByteArrayInputStream in = new ByteArrayInputStream("exit\n".getBytes());
    System.setIn(in);
    controller.executeProgram();
    assertTrue(mockView.containsMessage("Welcome to the Image Handler Program."));
    assertTrue(mockView.containsMessage("Exiting the program."));
  }

  @Test
  public void testExecuteProgramWithMultipleCommands() {
    String input = "blur source dest\nsave test.jpg dest\nexit\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    controller.executeProgram();
    assertEquals("blur", mockModel.lastCommand);
  }

  @Test
  public void testRunScript() throws IOException {
    File tempFile = createTempScriptFile("blur source dest\nsave test.jpg dest\n");
    controller.runScript(tempFile.getAbsolutePath());
    assertEquals("blur", mockModel.lastCommand);
    tempFile.delete();
  }

  @Test(expected = IOException.class)
  public void testRunScriptWithInvalidFile() throws IOException {
    controller.runScript("nonexistent.txt");
  }

  @Test
  public void testRunScriptWithComments() throws IOException {
    File tempFile = createTempScriptFile("#comment\nblur source dest\n#another " +
        "comment\n");
    controller.runScript(tempFile.getAbsolutePath());
    assertEquals("blur", mockModel.lastCommand);
    tempFile.delete();
  }

  private File createTempScriptFile(String content) throws IOException {
    File tempFile = File.createTempFile("test-script", ".txt");
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(content);
    }
    return tempFile;
  }

  @Test
  public void testMaskedOperation() {
    controller.handleCommand("sepia manhattan manhattan-mask manhattan-sepia-mask");
    assertEquals("masked-sepia", mockModel.lastCommand);
    assertEquals("manhattan", mockModel.lastSourceImage);
    assertEquals("manhattan-sepia-mask", mockModel.lastDestImage);
  }

  @Test
  public void testRGBSplit() {
    controller.handleCommand("rgb-split original red green blue");
    assertEquals("rgb-split", mockModel.lastCommand);
    assertEquals("original", mockModel.lastSourceImage);
    assertEquals("red,green,blue", mockModel.lastDestImage);
  }

  @Test
  public void testRGBCombine() {
    controller.handleCommand("rgb-combine combined red green blue");
    assertEquals("rgb-combine", mockModel.lastCommand);
    assertEquals("red,green,blue", mockModel.lastSourceImage);
    assertEquals("combined", mockModel.lastDestImage);
  }
}
