package pl.sda.javawwa18.imitations;

import java.util.List;

public interface NotesRepository {

    void save(Note note);
    List<Note> getAllNotesOf(String fullName);
    void removeAll();

}
