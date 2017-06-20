package cs3500.music.controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cs3500.music.view.IMusicEditorView;

/**
 * Created by Hoyin on 6/19/2017.
 */
public class ViewMouseListener implements MouseListener {

  private IMusicEditorView view;

  public ViewMouseListener(IMusicEditorView view) {
    this.view = view;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.println(e.getLocationOnScreen().toString());

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
