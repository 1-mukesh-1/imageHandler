package imagehandler.controller.mocking;

import imagehandler.view.IImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * MockImageView is a class that implements the IImageView interface for testing.
 */
public class MockImageView implements IImageView {
  public List<String> messages;
  public List<String> commandHistory;

  public MockImageView() {
    this.messages = new ArrayList<>();
    this.commandHistory = new ArrayList<>();
  }

  @Override
  public void showMessage(String message) {
    messages.add(message);
  }

  public void clearMessages() {
    messages.clear();
  }

  public String getLastMessage() {
    return messages.isEmpty() ? null : messages.get(messages.size() - 1);
  }

  public boolean containsMessage(String message) {
    return messages.stream().anyMatch(m -> m.contains(message));
  }
}
