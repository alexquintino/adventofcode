package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static day2.Main.Direction.DECREASING;
import static day2.Main.Direction.INCREASING;
import static day2.Main.isSafeReport;

public class Main {
    public static void main(String[] args) throws IOException {
        Path examplePath = Paths.get("2024", "day2", "example.txt");
        Path inputPath = Paths.get("2024", "day2", "input.txt");
        List<String> exampleReports = Files.readAllLines(examplePath);
        List<String> inputReports = Files.readAllLines(inputPath);

        System.out.println("Part 1 (example): " + countValidReports(exampleReports));
        System.out.println("Part 1 (input): " + countValidReports(inputReports));

        System.out.println("Part 2 (example): " + countValidReports(exampleReports, false));
        System.out.println("Part 2 (input): " + countValidReports(inputReports, false));
    }

    private static long countValidReports(List<String> reports) {
        return countValidReports(reports, true);
    }

    private static long countValidReports(List<String> reports, boolean isStrict) {
        return reports.stream()
                .map(Main::toLevels)
                .filter(lvl -> isSafeReport(lvl, isStrict))
                .count();
    }

    private static List<Integer> toLevels(String report) {
        return Arrays.stream(report.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    enum Direction {
        INCREASING,
        DECREASING
    }

    static boolean isSafeReport(List<Integer> levels, boolean isStrict) {
        Direction mainDirection = INCREASING;
        boolean toleratedBadLevel = false;

        for (int i = 0; i < levels.size() - 1; i++) {
            Integer distance = levels.get(i + 1) - levels.get(i);
            var newDirection = distance >= 0 ? INCREASING : DECREASING;
            if (i == 0) mainDirection = newDirection;
            if (Math.abs(distance) < 1 || Math.abs(distance) > 3 || newDirection != mainDirection) {
                if (isStrict) {
                    return false;
                } else {
                    if (toleratedBadLevel) {
                        return false;
                    }
                    toleratedBadLevel = true;
                    levels.remove(i);
                    i = -1;
                }
            }
        }
        return true;
    }

}
class Tests {
    public static void main(String[] args) {
        assert isSafeReport(List.of(7, 6, 4, 2, 1), true);
        assert !isSafeReport(List.of(1, 2, 7, 8, 9), true);
        assert !isSafeReport(List.of(9, 7, 6, 2, 1), true);
        assert !isSafeReport(List.of(1, 3, 2, 4, 5), true);
        assert !isSafeReport(List.of(8, 6, 4, 4, 1), true);
        assert isSafeReport(List.of(1, 3, 6, 7, 9), true);

        assert isSafeReport(of(7, 6, 4, 2, 1), false);
        assert !isSafeReport(of(1, 2, 7, 8, 9), false);
        assert !isSafeReport(of(9, 7, 6, 2, 1), false);
        assert isSafeReport(of(1, 3, 2, 4, 5), false);
        assert isSafeReport(of(8, 6, 4, 4, 1), false);
        assert isSafeReport(of(1, 3, 6, 7, 9), false);

        assert !isSafeReport(of(77, 78, 79, 80, 79, 76), false);
        assert !isSafeReport(of(30, 33, 34, 36, 39, 36, 38, 40), false);
        assert !isSafeReport(of(67, 69, 71, 73, 73, 72), false);
        assert !isSafeReport(of(15, 16, 16, 19, 21, 23, 23), false);
        assert !isSafeReport(of(87, 88, 90, 92, 92, 96), false);
    }

    private static List<Integer> of(int... digits) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int digit : digits) {
            list.add(digit);
        }
        return list;
    }
}
