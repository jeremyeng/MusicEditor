package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

import cs3500.music.model.Note;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IMusicEditorView<Note> {

  private final JPanel musicEditorPanel;
  private final ScorePanel scorePanel; // You may want to refine this to a subtype of JPanel
  private final PianoPanel pianoPanel;


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
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void playNote(List<List<List<Integer>>> info, long tempo) throws InvalidMidiDataException {

  }


  @Override
  public void setListener(ActionListener action, KeyListener key) {
    this.addKeyListener(key);
    this.scorePanel.addKeyListener(key);
    this.pianoPanel.addKeyListener(key);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void updateCurrentBeat(int beat) {
    this.scorePanel.updateCurrentBeat(beat);
    this.pianoPanel.updateCurrentBeat(beat);
  }

  @Override
  public void setNoteRange(List noteRange) {
    this.scorePanel.setNoteRange(noteRange);
    this.pianoPanel.setNoteRange(noteRange);
  }

  @Override
  public void setDuration(int duration) {
    this.scorePanel.setDuration(duration);
    this.pianoPanel.setDuration(duration);
  }

  @Override
  public void setNoteMap(Map<Note, List<String>> notes) {
    this.scorePanel.setNoteMap(notes);
    this.pianoPanel.setNoteMap(notes);
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
