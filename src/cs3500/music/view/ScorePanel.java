package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import cs3500.music.model.Note;


/**
 * Created by Hoyin on 6/12/2017.
 */
public class ScorePanel extends JPanel {

  private final int SINGLE_NOTE_WIDTH = 25;
  private final int SINGLE_NOTE_HEIGHT = 20;
  private final int SCORE_X_POSITION = 50;
  private final int LOWEST_NOTE_Y_POSITION = 300;
  private final int GAP_BETWEEN_NOTE_AND_BLOCKS_X = 30;
  private final int GAP_BETWEEN_NOTE_AND_BLOCKS_Y = 15;
  private List<Note> noteRange = new ArrayList<>();
  private int duration = 0;

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);
    for (int i = 0; i < noteRange.size(); i++) {
      drawBlock(SCORE_X_POSITION, LOWEST_NOTE_Y_POSITION - i * SINGLE_NOTE_HEIGHT, g2d);
    }
    for (int i = 0; i < SINGLE_NOTE_WIDTH * duration + 1; i += 4 * SINGLE_NOTE_WIDTH) {
      g2d.drawString(Integer.toString(i / SINGLE_NOTE_WIDTH),
              SCORE_X_POSITION + i,
              LOWEST_NOTE_Y_POSITION - noteRange.size() * SINGLE_NOTE_HEIGHT);
    }
    for (int i = 0; i < noteRange.size(); i++) {
      g2d.drawString(this.noteRange.get(i).toString(),
              SCORE_X_POSITION - GAP_BETWEEN_NOTE_AND_BLOCKS_X,
              LOWEST_NOTE_Y_POSITION - i * SINGLE_NOTE_HEIGHT + GAP_BETWEEN_NOTE_AND_BLOCKS_Y );
    }

  }

  protected void drawBlock(int x, int y, Graphics2D g2d) {
    g2d.drawRect(x, y, SINGLE_NOTE_WIDTH * duration, SINGLE_NOTE_HEIGHT);
    for (int i = 0; i < SINGLE_NOTE_WIDTH * duration; i += 4 * SINGLE_NOTE_WIDTH) {
      g2d.drawLine(x + i, y, x + i, y + SINGLE_NOTE_HEIGHT);
    }
  }

  protected void setNoteRange(List<Note> notes) {
    this.noteRange = notes;
  }

  protected void setDuration(int duration) {
    this.duration = duration;
  }


}
