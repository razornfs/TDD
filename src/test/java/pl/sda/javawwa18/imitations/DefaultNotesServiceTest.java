package pl.sda.javawwa18.imitations;

public class DefaultNotesServiceTest {

    private NotesService notesService =
            DefaultNotesService.createWith(new ImitatedNotesRepository());

}
