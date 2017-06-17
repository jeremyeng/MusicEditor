package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Emulates a Java Receiver and logs every call to send in a StringBuilder.
 */
public class MockReceiver implements Receiver{
  private StringBuilder log;
  private long tempo;
  /**
   * Creates a new Mock Java Receiver that logs every call to send in the given String Builder.
   */
  public MockReceiver(StringBuilder log, long tempo) {
    this.tempo = tempo;
    this.log = log;
    log.append("tempo ");
    log.append(this.tempo);
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage msg = this.shortenMessage(message);
//    String command = this.parseCommand(msg);
//    if (command == null) {
//      return;
//    }

    switch (msg.getCommand()) {
      case ShortMessage.NOTE_ON:
        log.append("\nnote ");
        log.append(timeStamp / this.tempo);
        break;
      case ShortMessage.NOTE_OFF:
        log.append(" ");
        log.append(timeStamp / this.tempo);
        log.append(" ");
        log.append(msg.getChannel() + 1);
        log.append(" ");
        log.append(msg.getData1());
        log.append(" ");
        log.append(msg.getData2());
        break;
      default:
        break;
    }
//    log.append(command);
//    log.append("Instrument: ");
//    log.append(msg.getChannel() + " ");
//    log.append("Pitch: ");
//    log.append(msg.getData1() + " ");
//    log.append("Volume: ");
//    log.append(msg.getData2() + " ");
//    log.append("TimeStamp: ");
//    log.append(timeStamp);
//    log.append("\n");
  }

  /**
   * Parses the command of a short message into a string that represents what it does.
   * @param msg the ShortMessage to parse.
   * @return a String representation of the ShortMessage.
   */
  private String parseCommand(ShortMessage msg) {
    switch (msg.getCommand()) {
      case ShortMessage.NOTE_ON:
        return "Note ON: ";
      case ShortMessage.NOTE_OFF:
        return "Note OFF: ";
      default:
        return null;
    }
  }

  /**
   * Converts the midi message to a ShortMessage
   * @param message the message to convert
   * @return the shortenedMessage
   */
  private ShortMessage shortenMessage(MidiMessage message) {
    if (message instanceof  ShortMessage) {
      return (ShortMessage) message;
    }
    return null;
  }

  @Override
  public void close() {

  }

  public StringBuilder getLog() {
    return new StringBuilder(this.log);
  }
}
