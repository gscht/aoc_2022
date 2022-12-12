package dev.gscht.aoc.day11;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day11Application {

  public static void main(String[] args) throws IOException {
    var lines = Files.readAllLines(Path.of("/Users/gscht/Downloads/day11/input.txt"));

    var monkeys = new ArrayList<Monkey>(8);
    Monkey currentMonkey = null;
    for (var line : lines) {
      if (line.length() == 0) {
        continue;
      }
      if (line.startsWith("Monkey ")) {
        currentMonkey = new Monkey();
        monkeys.add(currentMonkey);
      } else if (line.trim().startsWith("Starting items: ")) {
        Stream.of(line.trim().substring("Starting items: ".length()).split(", "))
            .map(BigInteger::new)
            .forEach(currentMonkey.getItems()::add);
      } else if (line.trim().startsWith("Operation: ")) {
      } else if (line.trim().startsWith("Test: divisible by ")) {
        currentMonkey.setTest(
            Integer.parseInt(line.trim().substring("Test: divisible by ".length())));
      } else if (line.trim().startsWith("If true: throw to monkey ")) {
        currentMonkey.setTrueToMonkey(
            Integer.parseInt(line.trim().substring("If true: throw to monkey ".length())));
      } else if (line.trim().startsWith("If false: throw to monkey ")) {
        currentMonkey.setFalseToMonkey(
            Integer.parseInt(line.trim().substring("If false: throw to monkey ".length())));
      } else {
        throw new IllegalArgumentException("Unknown line " + line);
      }
    }
    System.out.println(monkeys);
    long lcm = monkeys.get(0).getTest();
    for (int i = 1; i < monkeys.size(); i++) {
      lcm *= monkeys.get(i).getTest();
    }

    for (var round = 1; round <= 10_000; round++) {
      for (var monkey = 0; monkey < monkeys.size(); monkey++) {
        currentMonkey = monkeys.get(monkey);
        while (currentMonkey.getItems().size() > 0) {
          currentMonkey.inspectItem();
          var currentItem = currentMonkey.getItems().remove(0);
          var worryLevel = currentItem;
          switch (monkey) {
            case 0 -> worryLevel = worryLevel.multiply(BigInteger.valueOf(17));
              // case 0 -> worryLevel =
              // worryLevel.multiply(BigInteger.valueOf(19L)); // worryLevel = worryLevel * 19;
            case 1 -> worryLevel = worryLevel.add(BigInteger.valueOf(8));
              // case 1 -> worryLevel =
              // worryLevel.add(BigInteger.valueOf(6)); // worryLevel = worryLevel + 6;
            case 2 -> worryLevel = worryLevel.add(BigInteger.valueOf(6));
              // case 2 -> worryLevel =
              // worryLevel.multiply(worryLevel); // worryLevel = worryLevel * worryLevel;
            case 3 -> worryLevel = worryLevel.multiply(BigInteger.valueOf(19));
              // case 3 -> worryLevel =
              // worryLevel.add(BigInteger.valueOf(3)); // worryLevel = worryLevel + 3;
            case 4 -> worryLevel =
                worryLevel.add(BigInteger.valueOf(7)); // worryLevel = worryLevel + 7;
            case 5 -> worryLevel =
                worryLevel.multiply(worryLevel); // worryLevel = worryLevel * worryLevel;
            case 6 -> worryLevel =
                worryLevel.add(BigInteger.valueOf(1)); // worryLevel = worryLevel + 1;
            case 7 -> worryLevel =
                worryLevel.add(BigInteger.valueOf(2)); // worryLevel = worryLevel + 2;
          }
          // worryLevel = Math.floorDiv(worryLevel, 3);
          worryLevel = worryLevel.mod(BigInteger.valueOf(lcm));
          if (worryLevel
              .mod(BigInteger.valueOf(currentMonkey.getTest()))
              .equals(BigInteger.valueOf(0))) {
            monkeys.get(currentMonkey.getTrueToMonkey()).getItems().add(worryLevel);
          } else {
            monkeys.get(currentMonkey.getFalseToMonkey()).getItems().add(worryLevel);
          }
        }
      }
    }

    var monkeySorted = monkeys.stream().sorted().toList();

    monkeySorted.forEach(System.out::println);
    System.out.println();

    var result =
        monkeySorted.get(monkeySorted.size() - 1).getInspectedItems()
            * monkeySorted.get(monkeySorted.size() - 2).getInspectedItems();
    System.out.println(result);

    SpringApplication.run(Day11Application.class, args);
  }
}
