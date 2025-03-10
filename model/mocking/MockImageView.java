package imagehandler.model.mocking;

import imagehandler.view.IImageView;

/**
 * This class is used to test the ImageView class.
 */
class MockImageView implements IImageView {
  private String message;

  @Override
  public void showMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}