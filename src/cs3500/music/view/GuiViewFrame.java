package cs3500.music.view;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import cs3500.music.model.IReadOnlyMusicEditor;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements IGuiView {

  private final GuiViewPanel guiViewPanel;
  private IReadOnlyMusicEditor model;
  private Map<Integer, List<String>> combineNoteMap = new TreeMap<>();
  private int duration = 0;
  private int currentBeat = 0;


  /**
   * Creates new GuiView by adding in the score and then piano panel.
   */
  public GuiViewFrame() {
    super();
    this.setTitle("Music Editor");
    this.guiViewPanel = new GuiViewPanel();
    this.setSize(1600, 2000);
    this.setContentPane(this.guiViewPanel);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void update(IReadOnlyMusicEditor model) {
    this.model = model;
    this.combineNoteMap = model.getCombinedNoteMap();
    this.guiViewPanel.update(model);
  }

  @Override
  public void addKeyListener(KeyListener key) {
    this.guiViewPanel.addKeyListener(key);
  }

  @Override
  public void addMouseListener(MouseListener mouse) {
    this.guiViewPanel.addMouseListener(mouse);
  }

  @Override
  public void updateCurrentBeat(int beat) {
    if (this.currentBeat + beat >= 0 || this.currentBeat + beat <= this.duration) {
      this.currentBeat += beat;
      this.guiViewPanel.updateCurrentBeat(beat);
      this.repaint();
    }
  }

  @Override
  public void setDuration(int duration) {
    this.duration = duration;
    this.guiViewPanel.setDuration(duration);
  }


  @Override
  public void setCombineNoteMap(Map<Integer, List<String>> notes) {
    this.combineNoteMap = notes;
    this.guiViewPanel.setCombineNoteMap(notes);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(100, 100);
  }

  @Override
  public int getCurrentBeat() {
    return this.guiViewPanel.getCurrentBeat();
  }

  @Override
  public int noteClicked() {
    return this.guiViewPanel.noteClicked();
  }

  @Override
  public int getDuration() {
    return this.guiViewPanel.getDuration();
  }

}
