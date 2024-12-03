import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) throws IOException {
        Path examplePath = Paths.get("1","example.txt");
        Path inputPath = Paths.get("1", "input.txt");

        partOne(examplePath);
        partOne(inputPath);

        partTwo(examplePath);
        partTwo(inputPath);
    }

    static void partOne(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);

        List<Integer> firstColumn = parseColumn(lines, 0);
        List<Integer> secondColumn = parseColumn(lines, 1);

        firstColumn.sort(Comparator.naturalOrder());
        secondColumn.sort(Comparator.naturalOrder());

        List<Integer> distances = new ArrayList<>(firstColumn.size());
        for (int i = 0; i < firstColumn.size(); i++) {
            var distance = Math.abs(firstColumn.get(i) - secondColumn.get(i));
            distances.add(i, distance);
        }

        int sum = distances.stream().mapToInt(Integer::intValue).sum();

        System.out.println("Part ONE " + path + " : " + sum);
    }

    static void partTwo(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);

        List<Integer> firstColumn = parseColumn(lines, 0);
        List<Integer> secondColumn = parseColumn(lines, 1);

        Map<Integer, Integer> locationIdToCount = new LinkedHashMap<>();
        secondColumn.forEach(id -> locationIdToCount.compute(id, (key, oldValue) -> oldValue == null ? 1 : oldValue+1));

        int similarityScore = firstColumn.stream()
                .mapToInt(locationId -> locationId * locationIdToCount.getOrDefault(locationId, 0))
                .sum();
        System.out.println("Part TWO " + path + " : " + similarityScore);
    }


    static List<Integer> parseColumn(List<String> lines, int column) {
        return lines.stream()
                .map(l -> l.split("   ")[column])
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayList::new)); // so it's mutable
    }
}