package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.model.Note;

/**
 * Represents the region where the keys of the piano are drawn.
 */
public class PianoPanel extends JPanel {

  private final int WHITE_KEY_LENGTH = 300;
  private final int BLACK_KEY_LENGTH = 130;
  private final int WHITE_KEY_WIDTH = 20;
  private final int BLACK_KEY_WIDTH = (WHITE_KEY_WIDTH / 2);

  private List<Note> noteRange = new ArrayList<>();
  private Map<Note, List<String>> noteMap = new TreeMap<>();
  private int currentBeat = 0;
  private int duration = 0;

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.BLACK);


    int x = 0;
    for (int i = 0; i < 10; i++) {
      this.drawWhiteKeys(x, 20, g2d);
      this.drawBlackKeys(x + (int) (WHITE_KEY_WIDTH * 0.75), 20, g2d);
      x += WHITE_KEY_WIDTH * 7;
    }

    for (Map.Entry<Note, List<String>> entry : noteMap.entrySet()) {
      if (entry.getValue().get(currentBeat) == "start" ||
              entry.getValue().get(currentBeat) == "continue") {
        System.out.println("Piano is drawing");
        g2d.setColor(Color.RED);
        g2d.fillRect(noteRange.indexOf(entry.getKey()) * WHITE_KEY_WIDTH, WHITE_KEY_LENGTH, 100,100 );
      }
    }


  }

  /**
   * Draws the black keys on the piano.
   * @param x the x-position in which the key is drawn
   * @param y the y-position in which the ket is drawn
   * @param g2d the image in which the key is going to be drawn
   */
  private void drawBlackKeys(int x, int y, Graphics2D g2d) {
    int curX = x;
    for (int i = 0; i < 6; i++) {
      if (i == 2) {
        curX += WHITE_KEY_WIDTH;
        continue;
      }
      g2d.fillRect(curX, y, BLACK_KEY_WIDTH, BLACK_KEY_LENGTH);
      curX += WHITE_KEY_WIDTH;
    }
  }

  /**
   * Draws the white kets on the piano
   * @param x the x-position in which the key is drawn
   * @param y the y-position in which the key is drawn
   * @param g2d the image in which the key is going to be drawn
   */
  private void drawWhiteKeys(int x, int y, Graphics2D g2d) {
    int curX = x;
    for (int i = 0; i < 7; i++) {
      g2d.drawRect(curX, y, WHITE_KEY_WIDTH, WHITE_KEY_LENGTH);
      curX += WHITE_KEY_WIDTH;
    }
  }

  protected void setNoteRange(List noteRange) {
    this.noteRange = noteRange;
  }

  protected void setNoteMap(Map<Note, List<String>> notes) {
    this.noteMap = notes;
  }

  protected void updateCurrentBeat(int beat) {
    if (! ((this.currentBeat + beat < 0)|| this.currentBeat + beat > this.duration)) {
      this.currentBeat += beat;
      this.repaint();
    }
  }

  protected void setDuration(int duration) {
    this.duration = duration;
  }

}
