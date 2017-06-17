package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.Note;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IGuiView {

  private final JPanel musicEditorPanel;
  private final ScorePanel scorePanel; // You may want to refine this to a subtype of JPanel
  private final PianoPanel pianoPanel;
  private IMusicEditor model;


  /**
   * Creates new GuiView by adding in the score and then piano panel.
   */
  public GuiViewFrame() {
    super();
    this.setTitle("Music Editor");

    this.musicEditorPanel = new JPanel();
    this.musicEditorPanel.setLayout(new BoxLayout(this.musicEditorPanel, BoxLayout.Y_AXIS));

    this.scorePanel = new ScorePanel();
    this.pianoPanel = new PianoPanel();
    scorePanel.setPreferredSize(scorePanel.getPreferredSize());
    pianoPanel.setPreferredSize(new Dimension(1500,1200));
    this.setSize(1600, 2000);
    JScrollPane scrollFrame = new JScrollPane(scorePanel);

    this.musicEditorPanel.add(scrollFrame);
    this.musicEditorPanel.add(pianoPanel);

    this.setContentPane(this.musicEditorPanel);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setListener(ActionListener action, KeyListener key) {
    this.addKeyListener(key);
    this.scorePanel.addKeyListener(key);
    this.pianoPanel.addKeyListener(key);
  }

  @Override
  public void updateCurrentBeat(int beat) {
    this.scorePanel.updateCurrentBeat(beat);
    this.pianoPanel.updateCurrentBeat(beat);
  }

  @Override
  public void setDuration(int duration) {
    this.scorePanel.setDuration(duration);
    this.pianoPanel.setDuration(duration);
  }

  @Override
  public void update(IMusicEditor model) {
    this.model = model;
  }


  @Override
  public void setCombineNoteMap(Map <Integer, List<String>> notes) {
    this.pianoPanel.setCombineNoteMap(notes);
    this.scorePanel.setCombineNoteMap(notes);
  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(100, 100);
  }

}
