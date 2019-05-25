package pl.sda.javawwa18.imitations;

import java.util.List;
//klasa imitacji
public class ImitatedNotesRepository implements NotesRepository {

    @Override
    public void save(Note note) {

    }

    @Override
    public List<Note> getAllNotesOf(String fullName) {
        return null;
    }

    @Override
    public void removeAll() {

    }
}
