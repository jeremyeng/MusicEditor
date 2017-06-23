package cs3500.music.controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IMusicEditorView;

/**
 * Handles Mouse inputs given from a view.
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
    // Not Needed
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // Not Needed
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // Not Needed
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // Not Needed
  }

}
