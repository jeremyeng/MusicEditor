package cs3500.music.controller;

/**
 * The controller interface for a Music Editor program.
 * The functions process commands, and get information form the model to give to the view.
 */
public interface IMusicEditorController {
  /**
   * Process a given string command and return status or error message as a string.
   * @param command the command given, plus any parameters.
   * @return status update or error message
   */
  String processCommand(String command);

  /**
   * Starts the Music Editor and gives command to the controller.
   */
  void go();
}
