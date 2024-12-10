package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static Pattern MEMORY_REGEX = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
    static Pattern PART2_REGEX = Pattern.compile("don't\\(\\).*?mul\\(\\d{1,3},\\d{1,3}\\).*?do\\(\\)");
    static Pattern MUL_REGEX = Pattern.compile("(\\d{1,3}),(\\d{1,3})");

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("2024", "day3", "input.txt"));
        System.out.println("Part 1:" + process(input));
        System.out.println("Part 2:" + process(cleanMemory(input)));
    }

    static int process(String corruptedMemory) {
        Matcher matcher = MEMORY_REGEX.matcher(corruptedMemory);
        int total = 0;
        while(matcher.find()) {
            total += calculateMul(matcher.group());
        }
        return total;
    }

    static String cleanMemory(String corruptedMemory) {
        return corruptedMemory.replaceAll(PART2_REGEX.toString(), "");
    }

    static int calculateMul(String mul) {
        Matcher matcher = MUL_REGEX.matcher(mul);
        matcher.find();
        return Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
    }
}

class Tests {
    public static void main(String[] args) {
        assert Main.process("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))") == 161;
        assert Main.process(Main.cleanMemory("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")) == 48;
    }
}
