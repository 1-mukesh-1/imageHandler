package imagehandler.controller.mocking;

import imagehandler.controller.imagecommands.ImageCommand;
import imagehandler.model.IImageUpdateOne;

/**
 * This class implements the ImageCommand interface.
 * The MockCommand class is used for mocking.
 */
class MockCommand implements ImageCommand {
  public final IImageUpdateOne model;
  public final String[] tokens;

  public MockCommand(IImageUpdateOne model, String[] tokens) {
    this.model = model;
    this.tokens = tokens;
  }

  @Override
  public void execute() {
    switch (tokens[0].toLowerCase()) {
      case "load":
        loadImage(tokens[1], tokens[2], (MockImageUpdateOne) model);
        break;
      case "save":
        saveImage(tokens[1], tokens[2], (MockImageUpdateOne) model);
        break;
      case "blur":
        model.blur(tokens[1], tokens[2]);
        break;
      case "brighten":
        model.brighten(Integer.parseInt(tokens[1]), tokens[2], tokens[3]);
        break;
      case "sharpen":
        model.sharpen(tokens[1], tokens[2]);
        break;
      case "sepia":
        if (tokens.length == 4) {
          model.applyMaskedOperation("sepia", tokens[1], tokens[2], tokens[3]);
        } else {
          model.applySepia(tokens[1], tokens[2]);
        }
        break;
      case "horizontal-flip":
        model.flipHorizontally(tokens[1], tokens[2]);
        break;
      case "vertical-flip":
        model.flipVertically(tokens[1], tokens[2]);
        break;
      case "red-component":
        model.redComponent(tokens[1], tokens[2]);
        break;
      case "green-component":
        model.greenComponent(tokens[1], tokens[2]);
        break;
      case "blue-component":
        model.blueComponent(tokens[1], tokens[2]);
        break;
      case "luma-component":
        model.lumaGrayscale(tokens[1], tokens[2]);
        break;
      case "value-component":
        model.valueComponent(tokens[1], tokens[2]);
        break;
      case "intensity-component":
        model.intensityComponent(tokens[1], tokens[2]);
        break;
      case "compress":
        model.compress(tokens[2], tokens[3], Double.parseDouble(tokens[1]));
        break;
      case "histogram":
        model.generateHistogram(tokens[1], tokens[2]);
        break;
      case "color-correct":
        model.colorCorrection(tokens[1], tokens[2]);
        break;
      case "levels-adjust":
        model.levelsAdjustment(tokens[4], Integer.parseInt(tokens[1]),
            Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), tokens[5]);
        break;
      case "downscale":
        model.downscaleImage(tokens[3], tokens[4], Integer.parseInt(tokens[1]),
            Integer.parseInt(tokens[2]));
        break;
      case "rgb-split":
        model.rgbSplit(tokens[1], tokens[2], tokens[3], tokens[4]);
        break;
      case "rgb-combine":
        model.rgbCombine(tokens[1], tokens[2], tokens[3], tokens[4]);
        break;
      default:
        System.out.println("Unknown command: " + tokens[0]);
    }
  }

  private void loadImage(String token, String token1, MockImageUpdateOne model) {
    System.out.println("Loading image: " + token);
    model.lastLoadedFile = token;
    model.lastDestImage = token1;
    model.lastCommand = "load";
  }

  private void saveImage(String token, String token1, MockImageUpdateOne model) {
    System.out.println("Saving image: " + token);
    model.lastSavedFile = token;
    model.lastDestImage = token1;
    model.lastCommand = "save";
  }
}