package pl.sda.javawwa18.imitations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileBasedNotesRepository implements NotesRepository {

    private final Path path;

    public static FileBasedNotesRepository from(final String path) {
        return new FileBasedNotesRepository(path);
    }

    private FileBasedNotesRepository(final String path) {
        this.path = Paths.get(path);
    }

    @Override
    public void save(Note note) {
        try {
            Files.write(path, format(note).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
        catch(IOException e) {
            System.err.println("IOException occured on saving new note :(");
        }
    }

    @Override
    public List<Note> getAllNotesOf(String fullName) {
        try(Stream<String> lines = Files.lines(path)) {
            return lines
                    .filter(line -> line.startsWith(fullName))
                    .map(line -> Note.of(line.substring(0, line.indexOf(":")),
                            Double.parseDouble(line.substring(line.indexOf(":") + 2))))
                    .collect(Collectors.toList());
        }
        catch(IOException e) {
            System.err.println("IOException occured on processing file lines :(");
        }
        return Collections.emptyList();
    }

    @Override
    public void removeAll() {
        try {
            Files.delete(path);
        }
        catch(IOException e) {
            System.err.println("IOException occured when tried to delete file :(");
        }
    }

    //%3.2f - np. 4.75
    private String format(Note note) {
        return String.format("%s: %3.2f\n", note.getFullName(), note.getScore());
    }
}
