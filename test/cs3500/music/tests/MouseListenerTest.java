package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.controller.ViewMouseListener;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests the mouse listener with mock runnables that keep track of whether or not they have
 * been run.
 */
public class MouseListenerTest {
  private ViewMouseListener mouseListener;
  MockRunnable clickedRunnable;

  /**
   * Initializes the test environment.
   */
  @Before
  public void initialize() {
    this.mouseListener = new ViewMouseListener();
    this.clickedRunnable = new MockRunnable();

    Map<Integer, Runnable> mouseClickMap = new HashMap<>();
    mouseClickMap.put(MouseEvent.MOUSE_CLICKED, clickedRunnable);
    this.mouseListener.setMouseClicksMap(mouseClickMap);
  }

  @Test
  public void testMouseClicked() {
    this.mouseListener.mouseClicked(new MouseEvent(new Button(), MouseEvent.MOUSE_CLICKED, 1,
            0, 0, 0, 1, false));
    assertEquals(true, this.clickedRunnable.hasBeenRun());
  }


}
