package cs3500.music.controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IMusicEditorView;

/**
 * Created by Hoyin on 6/19/2017.
 */
public class ViewMouseListener implements MouseListener {

  private Map<Integer, Runnable> mouseClicksMap;

  public void setMouseClicksMap(Map<Integer, Runnable> mouseClicksMap) {
    this.mouseClicksMap = mouseClicksMap;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    this.mouseClicksMap.get(e.getID()).run();
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

}
