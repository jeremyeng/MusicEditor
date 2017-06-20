package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Hoyin on 6/19/2017.
 */
public class ViewMouseListenerAdapter implements IMouseListenerAdapter {

  ViewMouseListener mouseListener;

  public ViewMouseListenerAdapter(ViewMouseListener mouseListener) {
    this.mouseListener = mouseListener;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.mouseListener.mouseClicked(e);
  }


}
