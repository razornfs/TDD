package pl.sda.javawwa18.imitations;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

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

        //other methods...

        return mockedNotesRepository;
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
