package pl.sda.javawwa18.imitations;

import java.util.List;

public interface NotesRepository {

    //Zapisuje w pamieci 'trwalej' ocene
    void save(Note note);
    //Zwraca wszystkie oceny danego ucznia
    List<Note> getAllNotesOf(String fullName);
    //Oproznia pamiec 'trwala' do '0'
    void removeAll();

}
