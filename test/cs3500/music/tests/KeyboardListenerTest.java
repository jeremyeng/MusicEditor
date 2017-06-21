package cs3500.music.tests;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.View;

import cs3500.music.controller.ViewKeyBoardListener;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests the keyboard listener with mock runnables that keep track of whether or not they have
 * been run.
 */
public class KeyboardListenerTest {

  private ViewKeyBoardListener listener;
  MockRunnable cRunnable;
  MockRunnable dRunnable;

  /**
   * Initializes the test environment.
   */
  @Before
  public void initialize() {
    cRunnable = new MockRunnable();
    dRunnable = new MockRunnable();

    Map<Character, Runnable> typedMap = new HashMap<>();
    typedMap.put('c', cRunnable);
    typedMap.put('d', dRunnable);

    Map<Integer, Runnable> pressedMap = new HashMap<>();
    pressedMap.put(KeyEvent.VK_C, cRunnable);

    Map<Integer, Runnable> releasedMap = new HashMap<>();
    releasedMap.put(KeyEvent.VK_D, cRunnable);
    pressedMap.put(KeyEvent.VK_D, dRunnable);

    listener = new ViewKeyBoardListener();
    listener.setKeyTypedMap(typedMap);
    listener.setKeyPressedMap(pressedMap);
    listener.setKeyReleasedMap(releasedMap);
  }

  @Test
  public void testKeyTyped() {
    listener.keyTyped(new KeyEvent(new Button(), KeyEvent.KEY_TYPED, System.currentTimeMillis(),
            0, KeyEvent.VK_UNDEFINED, 'c'));
    assertEquals(true, cRunnable.hasBeenRun());
    assertEquals(false, dRunnable.hasBeenRun());
  }

  @Test
  public void testKeyPressed() {
    listener.keyPressed(new KeyEvent(new Button(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
            0, KeyEvent.VK_C, 'c'));
    assertEquals(true, cRunnable.hasBeenRun());
    assertEquals(false, dRunnable.hasBeenRun());
  }

  @Test
  public void testKeyReleased() {
    listener.keyPressed(new KeyEvent(new Button(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(),
            0, KeyEvent.VK_D, 'c'));
    assertEquals(false, cRunnable.hasBeenRun());
    assertEquals(true, dRunnable.hasBeenRun());
  }
}
