package routefinder.adapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileIO {

    private final Path basePath;

    public FileIO(Path basePath) {
        this.basePath = basePath;
    }

    public FileIO() {
        this.basePath = Path.of("src", "main", "resources");
    }

    public List<String> readLines(String filename) {
        Path path = getPath(filename);
        return readLines(path);
    }

    private Path getPath(String filename) {
        return Paths.get(basePath.toString(), filename);
    }

    private List<String> readLines(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            System.out.printf("Can't read file %s!%n", path);
        }
        return Collections.emptyList();
    }

    public void write(String filename, String line) {
        Path path = getPath(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.out.printf("Can't write to file %s!%n", path);
        }
    }

}
