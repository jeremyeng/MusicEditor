package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * A class created to listen for keyboard events.
 */
public class ViewKeyBoardListener implements KeyListener {

  private Map<Character, Runnable> keyTypedMap;
  private Map<Integer, Runnable> keyPressedMap, keyReleasedMap;


  /**
   * Sets the map for key type events, which are characters in Java swing.
   * @param keyTypedMap
   */
  public void setKeyTypedMap(Map<Character, Runnable> keyTypedMap) {
    this.keyTypedMap = keyTypedMap;
  }

  /**
   * Sets the map for key typed events, which are represented as Integer in Java swing.
   * @param keyPressedMap
   */
  public void setKeyPressedMap(Map<Integer, Runnable> keyPressedMap) {
    this.keyPressedMap = keyPressedMap;
  }

  /**
   * Sets the map for key released events, which are represented as Integer in Java swing.
   * @param keyReleasedMap
   */
  public void setKeyReleasedMap(Map<Integer, Runnable> keyReleasedMap) {
    this.keyReleasedMap = keyReleasedMap;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (this.keyTypedMap.containsKey(e.getKeyChar())) {
      this.keyTypedMap.get(e.getKeyChar()).run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (this.keyPressedMap.containsKey(e.getKeyCode())) {
      this.keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (this.keyReleasedMap.containsKey(e.getKeyCode())) {
      this.keyReleasedMap.get(e.getKeyCode()).run();
    }
  }
}
