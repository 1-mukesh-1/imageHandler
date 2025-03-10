package imagehandler.model.mocking;

import imagehandler.view.IImageView;
import imagehandler.view.ImageView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * This class is used to test the ImageView class.
 */
public class ImageViewTest {
  private IImageView view;
  private MockSystemOut mockOutput;

  @Before
  public void setUp() {
    view = new ImageView();
    mockOutput = new MockSystemOut();
    mockOutput.setUpStreams();
  }

  @After
  public void tearDown() {
    mockOutput.restoreStreams();
  }

  @Test
  public void testShowMessage() {
    String message = "Test message";
    view.showMessage(message);
    assertEquals(message + System.lineSeparator(), mockOutput.getOutput());
  }

  @Test
  public void testShowEmptyMessage() {
    view.showMessage("");
    assertEquals(System.lineSeparator(), mockOutput.getOutput());
  }

  @Test
  public void testShowNullMessage() {
    view.showMessage(null);
    assertEquals("null" + System.lineSeparator(), mockOutput.getOutput());
  }
}