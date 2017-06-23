package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import javax.swing.*;

import cs3500.music.model.IReadOnlyMusicEditor;

/**
 * A representation of a visual Frame (i.e., a window) in Swing.
 */
public class GuiViewFrame extends javax.swing.JFrame implements IGuiView {


  private final ScorePanel scorePanel;
  private final PianoPanel pianoPanel;
  private JScrollPane scoreScroll;
  private IReadOnlyMusicEditor model;
  private Map<Integer, List<String>> combineNoteMap = new TreeMap<>();
  private int duration = 0;
  private int currentBeat = 0;
  public static final int SCREEN_WIDTH = 1600;
  public static final int SCREEN_HEIGHT = 2000;


  /**
   * Creates new GuiView by adding in the score and then piano panel.
   */
  public GuiViewFrame() {
    super();
    this.setTitle("Music Editor");

    JPanel musicEditorPanel = new JPanel();
    musicEditorPanel.setLayout(new BoxLayout(musicEditorPanel, BoxLayout.Y_AXIS));

    scorePanel = new ScorePanel();
    this.pianoPanel = new PianoPanel();
    scorePanel.setPreferredSize(scorePanel.getPreferredSize());
    pianoPanel.setPreferredSize(new Dimension(1500, 1200));
    this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    scoreScroll = new JScrollPane(scorePanel);
    musicEditorPanel.add(scoreScroll);
    musicEditorPanel.add(pianoPanel);
    this.setContentPane(musicEditorPanel);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void update(IReadOnlyMusicEditor model) {
    this.combineNoteMap = model.getCombinedNoteMap();
    if (this.model == null) {
      this.pianoPanel.setCombineNoteMap(this.combineNoteMap);
      this.pianoPanel.setUpKeys();
    }
    this.model = model;
    this.setDuration(model.getDuration());
    scorePanel.setCombineNoteMap(this.combineNoteMap);

  }

  @Override
  public void addKeyListener(KeyListener key) {
    scorePanel.addKeyListener(key);
    pianoPanel.addKeyListener(key);
  }

  @Override
  public void addMouseListener(MouseListener mouse) {
    this.pianoPanel.addMouseListener(mouse);
    this.scorePanel.addMouseListener(mouse);
  }

  @Override
  public void updateCurrentBeat(int beat) {
    this.currentBeat += beat;
    scorePanel.updateCurrentBeat(beat);
    this.pianoPanel.updateCurrentBeat(beat);
  }

  @Override
  public void setDuration(int duration) {
    this.duration = duration;
    scorePanel.setDuration(duration);
    this.pianoPanel.setDuration(duration);
  }


  @Override
  public void setCombineNoteMap(Map<Integer, List<String>> notes) {
    this.combineNoteMap = notes;
    this.pianoPanel.setCombineNoteMap(notes);
    this.pianoPanel.setUpKeys();
    scorePanel.setCombineNoteMap(notes);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 1000);
  }

  @Override
  public int getCurrentBeat() {
    return this.currentBeat;
  }

  @Override
  public int noteClicked() {
    return this.pianoPanel.getNoteClicked();
  }

  @Override
  public int getDuration() {
    return this.duration;
  }


}
