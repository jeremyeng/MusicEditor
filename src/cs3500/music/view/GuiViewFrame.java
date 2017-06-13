package cs3500.music.view;

import java.awt.*;
import java.awt.event.MouseListener; // Possibly of interest for handling mouse events

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IMusicEditorView {

  private final JPanel displayPanel; // You may want to refine this to a subtype of JPanel

  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {
    super();
    this.displayPanel = new PianoPanel();
    this.setTitle("Piano");
    this.displayPanel.setPreferredSize(new Dimension(500, 500));
    this.setSize(500, 500);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    this.pack();
  }

  @Override
  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public void playNote() throws InvalidMidiDataException {

  }

  @Override
  public void showText() {

  }

  @Override
  public void showVisual() {

  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(100, 100);
  }

}
