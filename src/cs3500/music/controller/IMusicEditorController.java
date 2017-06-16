package cs3500.music.controller;

/**
 * The controller interface for a Music Editor program.
 * The functions process commands, and get information form the model to give to the view.
 */
public interface IMusicEditorController<K> {

  /**
   * Starts the Music Editor and gives command to the controller.
   */
  void go();
}
