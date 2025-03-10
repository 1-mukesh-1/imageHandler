package imagehandler.model.mocking;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * This class is used to mock the System.out stream.
 */
public class MockSystemOut {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  /**
   * This method is used to set up the System.out stream.
   */
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  /**
   * This method is used to restore the System.out stream.
   */
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  /**
   * This method is used to get the output of the System.out stream.
   * @return The output of the System.out stream.
   */
  public String getOutput() {
    return outContent.toString();
  }
}