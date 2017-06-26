package cs3500.music.view;

import java.util.List;

/**
 * An interface specifying what a practice version of music editor needs to have
 */
public interface IPracticeView extends IMusicEditorView, IGuiView {

  /**
   * a method that returns a list of midi representation integers that
   * still needs to be played before the user can advance to the next beat.
   * @param beat the beat in which the user have to press the keys
   * @return a list of midi representation integers
   */
  List<Integer> getNotesToClick(int beat);

  /**
   * Obtain the guiview part of the practice view
   * @return the guiview part of the pratice view
   */
  IGuiView getGuiView();

}
