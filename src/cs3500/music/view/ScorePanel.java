package cs3500.music.view;

import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import cs3500.music.model.Note;


/**
 * Created by Hoyin on 6/12/2017.
 */

/**
 * A class that represents a panel of a music score. The panel consist of
 * beat numbers, name of the notes, note's action on each beat, and is seperated
 * by a line every 4 beats.
 */
public class ScorePanel extends JPanel {

  private final int SINGLE_NOTE_WIDTH = 25;
  private final int SINGLE_NOTE_HEIGHT = 20;
  private final int SCORE_X_POSITION = 50;
  private final int HIGHEST_NOTE_Y_POSITION = 50;
  private final int GAP_BETWEEN_NOTE_AND_BLOCKS_X = 50;
  private final int GAP_BETWEEN_NOTE_AND_BLOCKS_Y = -15;
  private final int GAP_BETWEEN_BEAT_AND_SCORE = 10;
  private Map<Integer, List<String>> combineNoteMap = new TreeMap<>();
  private int duration = 0;
  private int currentBeat = 0;

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);
    ArrayList<List<String>> notesToRender = new ArrayList<>();
    for (Map.Entry<Integer, List<String>> entry : combineNoteMap.entrySet()) {
      notesToRender.add(entry.getValue());
    }
    Collections.reverse(notesToRender);
    for (int i = 0; i < notesToRender.size(); i++) {
      drawBlock(notesToRender.get(i), SCORE_X_POSITION, HIGHEST_NOTE_Y_POSITION + i * SINGLE_NOTE_HEIGHT, g2d);
    }
    for (int i = 0; i < SINGLE_NOTE_WIDTH * duration + 1; i += 4 * SINGLE_NOTE_WIDTH) {
      g2d.drawString(Integer.toString(i / SINGLE_NOTE_WIDTH),
              SCORE_X_POSITION + i,
              HIGHEST_NOTE_Y_POSITION - GAP_BETWEEN_BEAT_AND_SCORE);
    }
    for (int i = 0; i < notesToRender.size(); i++) {
      g2d.drawString(new Note(143 - i, 0).toString(),
              SCORE_X_POSITION - GAP_BETWEEN_NOTE_AND_BLOCKS_X,
              HIGHEST_NOTE_Y_POSITION + i * SINGLE_NOTE_HEIGHT - GAP_BETWEEN_NOTE_AND_BLOCKS_Y );
    }
    drawRedLine(SCORE_X_POSITION + currentBeat * SINGLE_NOTE_WIDTH,
            HIGHEST_NOTE_Y_POSITION, g2d);
  }

  /**
   * Draws a note block base on the information given.
   * @param states a list of strings that describe the behavior of the notes on every beat.
   * @param x the x-position in which the block is going to be drawn.
   * @param y the y-position in which the block is going to be drawn.
   * @param g2d the image in which the block is going to be drawn.
   */
  protected void drawBlock(List<String> states, int x, int y, Graphics2D g2d) {
    g2d.drawRect(x, y, SINGLE_NOTE_WIDTH * duration, SINGLE_NOTE_HEIGHT);
    for (int i = 0; i < this.duration; i++) {
      renderNoteState(states.get(i), x + i * SINGLE_NOTE_WIDTH, y, g2d);
    }
    for (int i = 0; i < SINGLE_NOTE_WIDTH * duration; i += 4 * SINGLE_NOTE_WIDTH) {
      g2d.setColor(Color.BLACK);
      g2d.drawLine(x + i, y, x + i, y + SINGLE_NOTE_HEIGHT);
    }
  }

  /**
   * Sets a combine note map for the score panel to use.
   * @param combineNoteMap the combine note map that the score panel is going to reference on.
   */
  protected void setCombineNoteMap(Map<Integer, List<String>> combineNoteMap) {
    this.combineNoteMap = combineNoteMap;
  }

  /**
   * Sets the duration of the score panel.
   * @param duration the duration of the score panel.
   */
  protected void setDuration(int duration) {
    this.duration = duration;
  }

  /**
   * Renders a note base on its state.
   * @param state the state of the note.
   * @param x the x-position in which the note is going to be drawn.
   * @param y the y-position in which the note is going to be drawn.
   * @param g2d the image in which the note is going to be drawn on.
   */
  protected void renderNoteState(String state,int x, int y, Graphics2D g2d) {
    switch (state) {
      case "start": g2d.setColor(Color.BLACK);
      g2d.fillRect(x,y,SINGLE_NOTE_WIDTH - 1,SINGLE_NOTE_HEIGHT - 1);
      break;
      case "continue": g2d.setColor(Color.GREEN);
      g2d.fillRect(x,y,SINGLE_NOTE_WIDTH - 1,SINGLE_NOTE_HEIGHT - 1);
      break;
      case "rest": g2d.setColor(Color.WHITE);
      g2d.fillRect(x,y,SINGLE_NOTE_WIDTH - 1,SINGLE_NOTE_HEIGHT - 1);
      break;
      default: throw new IllegalArgumentException("Unsupported note state to render!");
    }
  }

  /**
   * Draws a red line that indicates the current beat.
   * @param x the x-position in which the line is going to be drawn.
   * @param y the y-position in which the line is going to be drawn.
   * @param g2d the image in which the note is going to be drawn on.
   */
  protected void drawRedLine(int x, int y, Graphics2D g2d) {
    g2d.setColor(Color.RED);
    g2d.drawLine(x,y,x,y + this.combineNoteMap.size() * SINGLE_NOTE_HEIGHT);
  }

  /**
   * Updates the current beat of the score panel with the given integer, advance if positive
   * and goes back otherwise.
   * @param beat the beat in which the score panel is going to be updates by.
   */
  protected void updateCurrentBeat(int beat) {
    if (! (this.currentBeat + beat < 0 || this.currentBeat + beat > this.duration)) {
      this.currentBeat += beat;
      this.repaint();
    }

  }

  @Override
  public boolean isFocusable() {
    return true;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(duration * SINGLE_NOTE_WIDTH + GAP_BETWEEN_NOTE_AND_BLOCKS_X + 50,
            combineNoteMap.size() * SINGLE_NOTE_HEIGHT + HIGHEST_NOTE_Y_POSITION + 50);
  }

}
