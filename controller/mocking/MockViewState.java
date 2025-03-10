package imagehandler.controller.mocking;

import imagehandler.view.IViewState;

/**
 * MockViewState is a class that implements the IViewState interface for testing.
 */
public class MockViewState implements IViewState {
  private boolean unsavedChanges;
  private String currentImageName;
  private String selectedOperation;
  private boolean splitViewEnabled;
  private int splitViewPercentage;

  /**
   * Constructor for MockViewState.
   */
  public MockViewState() {
    this.unsavedChanges = false;
    this.splitViewEnabled = false;
    this.splitViewPercentage = 50;
  }

  @Override
  public boolean hasUnsavedChanges() {
    return unsavedChanges;
  }

  @Override
  public void setUnsavedChanges(boolean unsavedChanges) {
    this.unsavedChanges = unsavedChanges;
  }

  @Override
  public String getCurrentImageName() {
    return currentImageName;
  }

  @Override
  public void setCurrentImageName(String imageName) {
    this.currentImageName = imageName;
  }

  @Override
  public String getSelectedOperation() {
    return selectedOperation;
  }

  @Override
  public void setSelectedOperation(String operation) {
    this.selectedOperation = operation;
  }

  @Override
  public boolean isSplitViewEnabled() {
    return splitViewEnabled;
  }

  @Override
  public void setSplitViewEnabled(boolean enabled) {
    this.splitViewEnabled = enabled;
  }

  @Override
  public int getSplitViewPercentage() {
    return splitViewPercentage;
  }

  @Override
  public void setSplitViewPercentage(int percentage) {
    this.splitViewPercentage = percentage;
  }
}
