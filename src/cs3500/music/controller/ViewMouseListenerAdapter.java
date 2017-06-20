package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import cs3500.music.view.GuiViewFrame;

/**
 * Created by Hoyin on 6/19/2017.
 */
public class ViewMouseListenerAdapter implements IMouseListenerAdapter {

  ViewMouseListener mouseListener;

  public ViewMouseListenerAdapter() {
    this.mouseListener = new ViewMouseListener(new GuiViewFrame());
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.mouseListener.mouseClicked(e);
  }


}
