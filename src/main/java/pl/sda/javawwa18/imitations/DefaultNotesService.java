package pl.sda.javawwa18.imitations;

import pl.sda.javawwa18.exception.NoSuchUserException;

import java.util.List;

public class DefaultNotesService implements NotesService {

    private final NotesRepository notesRepository;

    public static NotesService createWith(final NotesRepository notesRepository) {
        return new DefaultNotesService(notesRepository);
    }

    private DefaultNotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public void add(Note note) {
        if(note == null)
            throw new IllegalArgumentException();

        notesRepository.save(note);
    }

    public double averageOf(String fullName) {
        if(fullName == null)
            throw new IllegalArgumentException();

        List<Note> userNotes = notesRepository.getAllNotesOf(fullName);

        if(userNotes.isEmpty())
            throw new NoSuchUserException();

        return userNotes.stream()
                .mapToDouble(Note::getScore)
                .average()
                .orElse(0.0);
    }

    public void clear() {
        notesRepository.removeAll();
    }
}
