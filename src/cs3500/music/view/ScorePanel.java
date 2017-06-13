package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

/**
 * Created by Hoyin on 6/12/2017.
 */
public class ScorePanel extends JPanel{

  private final int SINGLE_NOTE_WIDTH = 25;
  private final int SINGLE_NOTE_HIEGHT = 20;
  private final int ALL_BEATS_LENGTH = 1000;
  private final int NUMBER_OF_NOTEBLOCKS = 12;

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);
    for (int i = 0; i < NUMBER_OF_NOTEBLOCKS; i++) {
      drawBlock(50, 500 - i * SINGLE_NOTE_HIEGHT,g2d);
    }
    for (int i = 0; i < ALL_BEATS_LENGTH; i += 4 * SINGLE_NOTE_WIDTH) {
      g2d.drawString(Integer.toString(i / SINGLE_NOTE_WIDTH),
              50 + i, 500 - NUMBER_OF_NOTEBLOCKS * SINGLE_NOTE_HIEGHT);
    }
    for (Pitch p : Pitch.values()) {
      g2d.drawString(new Note(p,3).toString(),20, 500 - (p.getPitchNumber() - 2) * SINGLE_NOTE_HIEGHT);
    }

  }

  protected void drawBlock(int x, int y, Graphics2D g2d) {
    g2d.drawRect(x,y,ALL_BEATS_LENGTH,SINGLE_NOTE_HIEGHT);
    for (int i = 0; i < ALL_BEATS_LENGTH; i += 4 * SINGLE_NOTE_WIDTH) {
      g2d.drawLine(x + i,y,x + i,y + SINGLE_NOTE_HIEGHT);
    }
  }


}
