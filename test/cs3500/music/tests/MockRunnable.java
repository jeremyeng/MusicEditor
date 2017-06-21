package cs3500.music.tests;

import org.junit.runner.RunWith;

/**
 * Mocks a runnable with a public boolean field that signals whether or not it has been run.
 */
public class MockRunnable implements Runnable{
  private boolean hasBeenRun;

  public MockRunnable() {
    this.hasBeenRun = false;
  }

  @Override
  public void run() {
    this.hasBeenRun = true;
  }

  /**
   * Determines if the runnable has been run at least once.
   * @return true if it has been run, false otherwise.
   */
  public boolean hasBeenRun() {
    return this.hasBeenRun;
  }
}
