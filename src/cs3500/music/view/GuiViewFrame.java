package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

import cs3500.music.model.Note;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IMusicEditorView {

  private final JPanel musicEditorPanel;
  private final ScorePanel scorePanel; // You may want to refine this to a subtype of JPanel
  private final PianoPanel pianoPanel;

  private List<Note> noteRange;
  private int duration;

  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {
    super();
    this.setTitle("Music Editor");

    this.musicEditorPanel = new JPanel();
    this.musicEditorPanel.setLayout(new BoxLayout(this.musicEditorPanel, BoxLayout.Y_AXIS));

    this.scorePanel = new ScorePanel();

    this.pianoPanel = new PianoPanel();

    this.setSize(1500, 750);

    this.musicEditorPanel.add(scorePanel);
    this.musicEditorPanel.add(pianoPanel);

    this.setContentPane(this.musicEditorPanel);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//    this.pack();
  }

  @Override
  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public void playNote() throws InvalidMidiDataException {

  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {

  }

  @Override
  public void setNoteRange(List noteRange) {
    this.scorePanel.setNoteRange(noteRange);
  }

  @Override
  public void setDuration(int duration) {
    this.scorePanel.setDuration(duration);
  }

  @Override
  public void showErrorMessage() {

  }

  @Override
  public void refresh() {

  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(100, 100);
  }

}
