package pl.sda.javawwa18.imitations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//klasa imitacji
public class ImitatedNotesRepository implements NotesRepository {

    List<Note> notesList = new ArrayList<>();

    @Override
    public void save(Note note) {
        notesList.add(note);
    }

    @Override
    public List<Note> getAllNotesOf(String fullName) {
        return notesList.stream()
                .filter(note -> note.getFullName().equals(fullName))
                .collect(Collectors.toList());
    }

    @Override
    public void removeAll() {
        notesList.clear();
        //notesList = new ArrayList<>();
    }
}
