package pl.sda.javawwa18.imitations;

import org.junit.Test;
import pl.sda.javawwa18.exception.NoSuchUserException;

import java.util.List;

import static org.junit.Assert.*;

public class DefaultNotesServiceTest {

    private NotesRepository notesRepository = new ImitatedNotesRepository();
    private NotesService notesService =
            DefaultNotesService.createWith(notesRepository);

    @Test(expected = IllegalArgumentException.class)
    public void add_null_note() {
        Note note = null;
        notesService.add(note);
    }

    @Test
    public void add() {
/*        try {
            notesService.averageOf("PW");
            //should fail, because no such user should be present in DB at the moment
            fail();
        }
        catch(NoSuchUserException ex) {
            //this is OK
        }*/

        assertTrue(notesRepository.getAllNotesOf("PW").isEmpty());

        Note note = Note.of("PW", 5.0);
        notesService.add(note);

        List<Note> notesOfPW = notesRepository.getAllNotesOf("PW");
        assertFalse(notesOfPW.isEmpty());
        assertEquals(1, notesOfPW.size());
        Note PWnote = notesOfPW.get(0);
        assertNotNull(PWnote);
        assertEquals("PW", PWnote.getFullName());
        assertEquals(5.0, PWnote.getScore(), 0.00001);
    }

    @Test(expected = NoSuchUserException.class)
    public void average_of_nonexistent() {
        notesService.averageOf("ABC");
    }

    //tutaj jest przydatna imitacja, bo w prosty sposob dodamy oceny dla danego ucznia
    //i na tej podstawie wyznaczymy jego srednia
    @Test
    public void average_of_existing() {
        notesRepository.save(Note.of("PW", 4));
        notesRepository.save(Note.of("PW", 6));
        notesRepository.save(Note.of("PW", 5));

        assertEquals(5.0, notesService.averageOf("PW"), 0.00001);
    }

    @Test(expected = NoSuchUserException.class)
    public void clear() {
        //PW dodany do DB
        notesRepository.save(Note.of("PW", 4));
        //DB wyczyszczone
        notesService.clear();
        //nie ma teraz PW w DB
        notesService.averageOf("PW");
    }

}
