package cs3500.music.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import cs3500.music.model.IReadOnlyMusicEditor;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IGuiView {


  private final ScorePanel scorePanel;
  private final PianoPanel pianoPanel;
  private Map<Integer, List<String>> combineNoteMap = new TreeMap<>();
  private int duration = 0;
  private int currentBeat = 0;


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
    this.setSize(1600, 2000);
    JScrollPane scrollFrame = new JScrollPane(scorePanel);

    musicEditorPanel.add(scrollFrame);
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
    // Does not require model
  }

  @Override
  public void addKeyListener(KeyListener key) {
    scorePanel.addKeyListener(key);
    pianoPanel.addKeyListener(key);
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
    scorePanel.setCombineNoteMap(notes);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(100, 100);
  }

  @Override
  public int getCurrentBeat() {
    return this.currentBeat;
  }

  @Override
  public int getDuration() {
    return this.duration;
  }

}
