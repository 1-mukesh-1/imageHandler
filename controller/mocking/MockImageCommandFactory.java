package imagehandler.controller.mocking;

import imagehandler.controller.imagecommands.IImageCommandFactory;
import imagehandler.controller.imagecommands.ImageCommand;
import imagehandler.model.IImageUpdateOne;
import imagehandler.view.IImageView;

/**
 * This class implements the IImageCommandFactory interface.
 * The MockImageCommandFactory class is used for mocking.
 */
public class MockImageCommandFactory implements IImageCommandFactory {
  private final IImageUpdateOne model;
  private IImageView view;
  public String lastCommandType;
  public String[] lastTokens;

  public MockImageCommandFactory(IImageUpdateOne model, IImageView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void setView(IImageView view) {
    this.view = view;
  }

  @Override
  public ImageCommand createCommand(String[] tokens) {
    if (tokens == null || tokens.length == 0) {
      throw new IllegalArgumentException("No command entered.");
    }

    lastCommandType = tokens[0].toLowerCase();
    lastTokens = tokens;

    return new MockCommand(model, tokens);
  }
}