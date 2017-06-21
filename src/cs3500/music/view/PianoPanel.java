package cs3500.music.view;

import com.sun.org.apache.regexp.internal.RE;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JPanel;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * Represents the region where the keys of the piano are drawn.
 */
public class PianoPanel extends JPanel {

  private final int WHITE_KEY_LENGTH = 300;
  private final int BLACK_KEY_LENGTH = 130;
  private final int WHITE_KEY_WIDTH = 20;
  private final int BLACK_KEY_WIDTH = (WHITE_KEY_WIDTH / 2);
  private final Color WHITE_KEY_DISPLAY_COLOR = Color.YELLOW;
  private final Color BLACK_KEY_DISPLAY_COLOR = Color.ORANGE;

  private Map<Integer, List<String>> noteMap = new TreeMap<>();
  private ArrayList<Integer> naturalNotes = new ArrayList<>();
  private ArrayList<Integer> sharpNotes = new ArrayList<>();
  private int currentBeat = 0;
  private int duration = 0;
  private ArrayList<Rectangle> whiteKeys = new ArrayList<>();
  private ArrayList<Rectangle> blackKeys = new ArrayList<>();
  private HashMap<Rectangle, Integer> whiteKeysMap = new HashMap<>();
  private HashMap<Rectangle, Integer> blackKeysMap = new HashMap<>();

  /**
   * Sets up the keys as rectangles.
   */
  public void setUpKeys() {
    for (Integer i : this.noteMap.keySet()) {
      if (new Note(i, 0).isSharp()) {
        this.sharpNotes.add(i);
      } else {
        this.naturalNotes.add(i);
      }
    }
    for (int i = 0; i < this.naturalNotes.size(); i++) {
      Rectangle curRect = new Rectangle(i * WHITE_KEY_WIDTH, 0, WHITE_KEY_WIDTH, WHITE_KEY_LENGTH);
      this.whiteKeys.add(curRect);
      this.whiteKeysMap.put(curRect,naturalNotes.get(i));
    }
    int blackKeyCounter = (int) (WHITE_KEY_WIDTH * 0.75);
    for (int i = 0; i < this.sharpNotes.size(); i++) {
      Rectangle curRect = new Rectangle(blackKeyCounter, 0,BLACK_KEY_WIDTH,BLACK_KEY_LENGTH);
      if (new Note(this.sharpNotes.get(i), 0).getPitch().equals(Pitch.DSharp) ||
              new Note(this.sharpNotes.get(i), 0).getPitch().equals(Pitch.ASharp)) {
        this.blackKeys.add(curRect);
        this.blackKeysMap.put(curRect,sharpNotes.get(i));
        blackKeyCounter += 2 * WHITE_KEY_WIDTH;
      }
      else {
        this.blackKeys.add(curRect);
        this.blackKeysMap.put(curRect,sharpNotes.get(i));
        blackKeyCounter += WHITE_KEY_WIDTH;
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);
    this.drawKeys(g2d);
  }

  private void drawKeys(Graphics2D g2d) {
    for (int i = 0; i < this.whiteKeys.size(); i++) {
      Rectangle curKey = this.whiteKeys.get(i);
      if (this.noteMap.get(naturalNotes.get(i)).get(currentBeat).equals("start")
              || this.noteMap.get(naturalNotes.get(i)).get(currentBeat).equals("continue")) {
        g2d.setColor(WHITE_KEY_DISPLAY_COLOR);
        g2d.fill(curKey);
        g2d.setColor(Color.BLACK);
        g2d.drawRect((int)curKey.getX(),(int)curKey.getY(), curKey.width, curKey.height);
      }
      else {
        g2d.setColor(Color.WHITE);
        g2d.fill(curKey);
        g2d.setColor(Color.BLACK);
        g2d.drawRect((int)curKey.getX(),(int)curKey.getY(), curKey.width, curKey.height);
      }
    }
    for (int i = 0; i < this.blackKeys.size(); i++) {
      Rectangle curKey = this.blackKeys.get(i);
      if (this.noteMap.get(sharpNotes.get(i)).get(currentBeat).equals("start")
              || this.noteMap.get(sharpNotes.get(i)).get(currentBeat).equals("continue")) {
        g2d.setColor(BLACK_KEY_DISPLAY_COLOR);
        g2d.fill(curKey);
        g2d.setColor(Color.BLACK);
        g2d.drawRect((int)curKey.getX(),(int)curKey.getY(), curKey.width, curKey.height);
      }
      else {
        g2d.setColor(Color.BLACK);
        g2d.fill(curKey);
        g2d.setColor(Color.BLACK);
        g2d.drawRect((int)curKey.getX(),(int)curKey.getY(), curKey.width, curKey.height);
      }
    }
  }

  /**
   * Sets the combined note map for the piano panel.
   *
   * @param notes the combined note map in which the piano panel is going to use.
   */
  protected void setCombineNoteMap(Map<Integer, List<String>> notes) {
    this.noteMap = notes;
  }

  /**
   * Updates the current beat of the piano panel base on the integer given, negative number
   * goes back a beat and positive number advance a beat.
   *
   * @param beat the number of beat in which the panel is going to move.
   */
  protected void updateCurrentBeat(int beat) {
    if (!((this.currentBeat + beat < 0) || this.currentBeat + beat >= this.duration)) {
      this.currentBeat += beat;
      this.repaint();
    }
  }

  /**
   * Sets the duration of the piano panel.
   *
   * @param duration the duration of the piano panel
   */
  protected void setDuration(int duration) {
    this.duration = duration;
  }


  /**
   * Returns the midi integer representation of what note is being clicked on the piano.
   * @return the midi integer representation of what note is being clicked on the piano, returns
   *          -1 when such note does not exist.
   */
  protected int getNoteClicked() {
    if (this.getMousePosition() != null) {
      for (Rectangle rect : blackKeysMap.keySet()) {
        if (rect.contains(this.getMousePosition())) {
          return this.blackKeysMap.get(rect);
        }
      }
      for (Rectangle rect : whiteKeysMap.keySet()) {
        if (rect.contains(this.getMousePosition())) {
          return this.whiteKeysMap.get(rect);
        }
      }
    }
    return -1;
  }


}
