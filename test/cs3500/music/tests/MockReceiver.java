package cs3500.music.tests;

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
