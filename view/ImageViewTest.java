package imagehandler.view;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * This class is used to test the ImageView class.
 */
public class ImageViewTest {

  private ImageView view;
  private ByteArrayOutputStream outputStream;

  @Before
  public void setUp() {
    view = new ImageView();
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  @Test
  public void testShowMessage() {
    String message = "Hello, World!";
    view.showMessage(message);
    assertEquals("Hello, World!\n", outputStream.toString());
  }

  @Test
  public void testShowEmptyMessage() {
    String message = "";
    view.showMessage(message);
    assertEquals("\n", outputStream.toString());
  }

  @Test
  public void testShowNullMessage() {
    String message = null;
    view.showMessage(message);
    assertEquals("null\n", outputStream.toString());
  }

  @Test
  public void testShowLongMessage() {
    String longMessage = "A".repeat(10000);
    view.showMessage(longMessage);
    assertEquals(longMessage + "\n", outputStream.toString());
  }

  @Test
  public void testShowSpecialCharactersMessage() {
    String message = "!@#$%^&*()_+[]{}|;':,.<>/?";
    view.showMessage(message);
    assertEquals("!@#$%^&*()_+[]{}|;':,.<>/?\n", outputStream.toString());
  }

  @Test
  public void testShowMultiLineMessage() {
    String multiLineMessage = "Line1\nLine2\nLine3";
    view.showMessage(multiLineMessage);
    assertEquals("Line1\nLine2\nLine3\n", outputStream.toString());
  }

  @Test
  public void testShowWhitespaceMessage() {
    String message = "   ";
    view.showMessage(message);
    assertEquals("   \n", outputStream.toString());
  }

  @Test
  public void testShowTabMessage() {
    String message = "\tTabbed message";
    view.showMessage(message);
    assertEquals("\tTabbed message\n", outputStream.toString());
  }
}