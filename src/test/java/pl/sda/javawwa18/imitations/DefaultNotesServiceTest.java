package pl.sda.javawwa18.imitations;

import org.junit.Test;
import pl.sda.javawwa18.exception.NoSuchUserException;

public class DefaultNotesServiceTest {

    private NotesService notesService =
            DefaultNotesService.createWith(new ImitatedNotesRepository());

    @Test(expected = IllegalArgumentException.class)
    public void add_null_note() {
        Note note = null;
        notesService.add(note);
    }

    @Test
    public void add() {

    }

    @Test(expected = NoSuchUserException.class)
    public void average_of_nonexistent() {
        notesService.averageOf("ABC");
    }

    //tutaj jest przydatna imitacja, bo w prosty sposob dodamy oceny dla danego ucznia
    //i na tej podstawie wyznaczymy jego srednia
    @Test
    public void average_of_existing() {

    }

    @Test
    public void clear() {

    }

}
