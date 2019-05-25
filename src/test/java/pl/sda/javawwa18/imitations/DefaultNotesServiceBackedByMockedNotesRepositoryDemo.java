package pl.sda.javawwa18.imitations;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import pl.sda.javawwa18.exception.NoSuchUserException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DefaultNotesServiceBackedByMockedNotesRepositoryDemo {

    private Map<String, List<Double>> notesJournal;

    public NotesRepository createMockedNotesRepository() {
        final NotesRepository mockedNotesRepository = mock(NotesRepository.class);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(final InvocationOnMock invocation) throws Throwable {
                final Note note = (Note) invocation.getArgument(0);
                updateJournal(note.getFullName(), note.getScore());
                return null;
            }
        }).when(mockedNotesRepository).save(any(Note.class));

        //assertjdemo methods...
        //getAllNotesOf -> 'proste mockowanie' -> per metoda testowa

        //clear -> answer
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(final InvocationOnMock invocation) throws Throwable {
                notesJournal.clear();
                return null;
            }
        }).when(mockedNotesRepository).removeAll();

        return mockedNotesRepository;
    }

    /* Analogiczny do (tylko ze z uzyciem obiektu imitacji):

        @Test
        public void average_of_existing() {
            notesRepository.save(Note.of("PW", 4));
            notesRepository.save(Note.of("PW", 6));
            notesRepository.save(Note.of("PW", 5));

            assertEquals(5.0, notesService.averageOf("PW"), 0.00001);
        }

     */
    @Test
    public void average_of_existing() {
        NotesRepository mockedNotesRepository = createMockedNotesRepository();
        when(mockedNotesRepository.getAllNotesOf("PW")).thenReturn(Arrays.asList(
                Note.of("PW", 5.0),
                Note.of("PW", 4.0),
                Note.of("PW", 6.0)
        ));
        NotesService notesService = DefaultNotesService.createWith(mockedNotesRepository);
        double avg = notesService.averageOf("PW");   //uzywa wewnatrz NotesRepository#getAllNotesOf("PW")
        assertEquals(5.0, avg, 0.00001);
        verify(mockedNotesRepository, only()).getAllNotesOf("PW");
        verify(mockedNotesRepository, never()).getAllNotesOf("Adam Miller");
    }

    @Test(expected = NoSuchUserException.class)
    public void average_of_nonexistent() {
        NotesRepository mockedNotesRepository = createMockedNotesRepository();
        when(mockedNotesRepository.getAllNotesOf("PW")).thenReturn(Collections.EMPTY_LIST);
        NotesService notesService = DefaultNotesService.createWith(mockedNotesRepository);
        notesService.averageOf("PW");
    }

    //ignorujemy ten test poniewaz sygnatura NotesRepository#save nie przewiduje wyrzucenia wyjatku IOException
    @Ignore
    @Test(expected = IOException.class)
    public void should_throw_on_persistance_layer_exception() {
        NotesRepository spyNotesRepository = spy(ImitatedNotesRepository.class);
        doThrow(new IOException()).when(spyNotesRepository).save(Note.of("PW", 3.0));
        NotesService notesService = DefaultNotesService.createWith(spyNotesRepository);
        notesService.add(Note.of("AM", 5.0));
        notesService.add(Note.of("PW", 3.0));
    }

    private void updateJournal(String name, Double score) {
        if(notesJournal.containsKey(name)) {
            List<Double> scores = notesJournal.get(name);
            scores.add(score);

            notesJournal.put(name, scores);
        }
        else {
            notesJournal.put(name, Collections.singletonList(score));
        }
    }

}
