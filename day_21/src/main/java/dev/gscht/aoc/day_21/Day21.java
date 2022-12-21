package dev.gscht.aoc.day_21;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
class Day21 implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    var input = Files.readAllLines(Path.of("./input.txt"));

    long low = 0;

    long high = 3000000000000L;
    long myNumber;

    var monkeys = new HashMap<String, Monkey>(input.size());
    do {
      myNumber = low + (long) Math.ceil((double) Math.abs(high - low) / 2);
      // myNumber = low + IntMath.divide(Math.abs(high - low), 2L, RoundingMode.CEILING);
      monkeys.clear();
      for (int j = 0; j < input.size(); j++) {
        var monkey = new Monkey(input.get(j));
        monkeys.put(monkey.name, monkey);
      }

      monkeys.get("humn").result = myNumber;

      var rootMonkey = monkeys.get("root");

      while (rootMonkey.result == null) {
        // replace
        monkeys.values().stream()
            .filter(m -> m.result == null)
            .forEach(
                monkeyWoResult -> {
                  if (monkeyWoResult.waitLeft != null) {
                    Optional.ofNullable(monkeys.get(monkeyWoResult.waitLeft).result)
                        .ifPresent(
                            res -> {
                              monkeyWoResult.left = res;
                              monkeyWoResult.waitLeft = null;
                            });
                  }
                  if (monkeyWoResult.waitRight != null) {

                    Optional.ofNullable(monkeys.get(monkeyWoResult.waitRight).result)
                        .ifPresent(
                            res -> {
                              monkeyWoResult.right = res;
                              monkeyWoResult.waitRight = null;
                            });
                  }
                });

        monkeys.values().forEach(Monkey::calculate);
      }
      System.out.println("My number %d".formatted(myNumber));
      System.out.println("Root monkey shouts %d".formatted(rootMonkey.result));
      System.out.println(
          "%d %d (%d)"
              .formatted(rootMonkey.left, rootMonkey.right, rootMonkey.left - rootMonkey.right));
      if (rootMonkey.left < rootMonkey.right) {
        high = monkeys.get("humn").result;
      } else if (rootMonkey.left > rootMonkey.right) {
        low = monkeys.get("humn").result;
      } else {
        break;
      }
    } while (monkeys.get("root").left != monkeys.get("root").right);
    System.out.println("FOUND! %d".formatted(myNumber));
    // 8846055863767561884 too high
    // 3458768105877386014
    // 314436729676894262 too high
    // 314436729676894262
    // 3592056845086
  }
}
