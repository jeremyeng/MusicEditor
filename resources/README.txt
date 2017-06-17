**********************************************************************************************************
Model:
**********************************************************************************************************
IMusicEditor:
    Defines the behavior for operations that an implementation of a Music Editor would have to
    follow.

    It is parameterized over an implementation of a note.

    This interface includes adding notes, removing notes, and combining with another implementation
    so that the notes play either consecutively or simultaneously. Only two instances of the same
    implementation may be combined.

    Compare to the original design from the previous assignment, the new MusicEditor interface now has
    more methods that allows the user to retrieve the information more easily. For example, the added
    getNoteState allows the user to know the status of a note at a given beat, and getMidiInfo allows
    the user to retrieve the music editor in a midi format.

MusicEditorModel:
    Implements the behavior define in the IMusicEditor interface.

    Represents a model for MusicEditor that takes in my implementation of a Note

    Stores notes in a Treemap of notes. Each Note corresponds to an Arraylist of
    Playbackinfo, which includes MusicStates (START, CONTINUE, REST) and the volume of the note,
    where the indices of the ArrayList represent the beat number of the piece. When a model is created,
    the initial number of beats must be passed in as an argument and all notes in all octaves are 
    initialized to be at rest for that duration.

IReadOnlyMusicEditor:
    Defines the behavior for a immutable version of IMusicEditor.

    Removes all methods that allow the user to change data in the model.

ReadOnlyMusicEditorModel:
    Implements a read-only IMusicEditor that contains a normal IMusicEditor as a field;

    Only allows the user to call methods that retrieve data from the IMusicEditor.

Note:
    Represents a note in the western style of music in pitch-octave notation, with an integer representing
    the instrument that is used to play the note.

    The pitch can only be one of the types in the Pitch enum, which represents the twelve notes
    in traditional western music.

    The octave can only be between 0 to 10 (inclusive) since that represents the range of human
    hearing.

    The integer is the instrument in midi representation.

Pitch:
    Represents the twelve pitches in western music.

    Each pitch has a number value associated with it in order to determine the ordering.

PlaybackInfo: 
    Represents the info needed for a midi note to play.

    Has a musicstate and the volume of a note as fields.

MusicStates:
    Represents the state of a note during any beat.

    A note will either be starting (START), continuing from the last start (CONTINUE), or at
    rest (REST).
*********************************************************************************************************

View:
******************************************************************************************************
IMusicEditorView:
    Defines the behavior that a view that represents a music editor should have, this interface is 
    extended by 
    IGuiView:
    which defines the functionality that a gui interface that represents the music model should have.
    IMidiView:
    which defines what is needed in order for a midi file to play in the music editor.

GuiViewFrame:
    A class that implements IGuiView and is responsible for displaying the visual representation of
    the editor to the user. It also passes the informtion needed for the two separate parts that make
    up the view:

    Score Panel:
    The score panel represents the state of the music, it spans from note C0 to B10. On the left 
    side of the score panel is the string that represents what pitch is being played at each block. 
    On top of the score panel is the beat number in which the notes are being played. Inside the 
    score panel start notes are represented as black squares, continue notes are represented as green
    square, and rest notes are represented as white squares. The score panel also has a red line that
    spans from the top of the block to the bottom of the block that indicates the beat the user is 
    currently on. The redline can be moved by the user by pressing left or right key, which go back
    and advance the note, respectively.

    Piano Panel:
    The piano panel is an representation of a what a piano would look like if corresponding notes are
    being played. When a start note or sustain ntoe is played on a certain beat, the corresponding parts
    of the piano  would also change color to indicate that it is currently being pressed. When a white 
    key is pressed the piano would change its color to orange; when a black key is pressed, the piano
    changes its color to orange.

MidiViewImpl:
    Uses the build-in java midi library to play music using the synthesiser and receiver. It receives
    information from the model, parse them and send them as midi messages to the receiver.

ViewFactory:
    Creates the corresponding view base on string input given by the user, currently the supported view
    are console, midi, and visual.

********************************************************************************************************
    
Controller:
*******************************************************************************************************
IMusicEditorController:
    Specify what functions a controller needs to have in order to process inputs from the view and send
    them to the model.

MusicEditorController:
    The implementation of the IMusicEditorController interface, also implements ActionListener
    and KeyListener in order to gather key commands from the user. The go method send information
    to the parts in the model where they are needed.
*******************************************************************************************************




    





