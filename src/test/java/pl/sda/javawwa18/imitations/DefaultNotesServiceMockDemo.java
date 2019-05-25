package pl.sda.javawwa18.imitations;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultNotesServiceMockDemo {

    private NotesService notesService;

    @Before
    public void before() {
        final NotesRepository notesRepository = mock(NotesRepository.class);

        when(notesRepository.getAllNotesOf("Przemyslaw Wozniak"))
                .thenReturn(Collections.singletonList(Note.of("Przemyslaw Wozniak", 5.0)));

        when(notesRepository.getAllNotesOf("Bob Dylan"))
                .thenReturn(Arrays.asList(Note.of("Bob Dylan", 5.0), Note.of("Bob Dylan", 3.0)));

        notesService = DefaultNotesService.createWith(notesRepository);
    }

    @Test
    public void calculate_average_score() {
        assertEquals(5.0, notesService.averageOf("Przemyslaw Wozniak"), 0.0001);
        assertEquals(4.0, notesService.averageOf("Bob Dylan"), 0.0001);
    }

}
