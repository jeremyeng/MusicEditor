package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.model.IReadOnlyMusicEditor;

/**
 * Created by Hoyin on 6/21/2017.
 */
public class GuiViewPanel extends JPanel {

  private final ScorePanel scorePanel;
  private final PianoPanel pianoPanel;
  private IReadOnlyMusicEditor model;
  private Map<Integer, List<String>> combineNoteMap = new TreeMap<>();
  private int duration = 0;
  private int currentBeat = 0;

  public GuiViewPanel() {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setSize(1600, 2000);
    this.scorePanel = new ScorePanel();
    this.pianoPanel = new PianoPanel();
    JScrollPane scrollPane = new JScrollPane(this.scorePanel);
    this.add(scrollPane);
    this.add(pianoPanel);
    scorePanel.setPreferredSize(scorePanel.getPreferredSize());
    pianoPanel.setPreferredSize(new Dimension(1500, 1200));
  }

  public void update(IReadOnlyMusicEditor model) {
    this.model = model;
    this.combineNoteMap = model.getCombinedNoteMap();
    this.pianoPanel.setCombineNoteMap(this.combineNoteMap);
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

  public void updateCurrentBeat(int beat) {
    this.currentBeat += beat;
    scorePanel.updateCurrentBeat(beat);
    this.pianoPanel.updateCurrentBeat(beat);
    this.repaint();
  }

  public void setDuration(int duration) {
    this.duration = duration;
    scorePanel.setDuration(duration);
    this.pianoPanel.setDuration(duration);
  }

  public void setCombineNoteMap(Map<Integer, List<String>> notes) {
    this.combineNoteMap = notes;
    this.pianoPanel.setCombineNoteMap(notes);
    this.pianoPanel.setUpKeys();
    scorePanel.setCombineNoteMap(notes);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(100, 100);
  }

  public int getCurrentBeat() {
    return this.currentBeat;
  }

  public int noteClicked() {
    return this.pianoPanel.getNoteClicked();
  }

  public int getDuration() {
    return this.duration;
  }

}
