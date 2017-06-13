package cs3500.music.view;

import java.awt.*;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IMusicEditorView {

  private final JPanel musicEditorPanel;
  private final JPanel scorePanel; // You may want to refine this to a subtype of JPanel
  private final JPanel pianoPanel;

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

    this.setSize(2000, 700);

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
