package dev.gscht.aoc.day_21;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
class Day21 implements ApplicationRunner {

  private Map<String, Monkey> waitingForLeft = new HashMap<>();
  private Map<String, Monkey> waitingForRight = new HashMap<>();

  @Override
  public void run(ApplicationArguments args) throws Exception {
    var input = Files.readAllLines(Path.of("./input.txt"));
    var monkeys = new ArrayList<Monkey>(input.size());
    var rootIndex = -1;
    for (int i = 0; i < input.size(); i++) {
      var monkey = new Monkey(input.get(i));
      monkeys.add(monkey);
      if (monkey.name.equals("root")) {
        rootIndex = i;
      }
      if (monkey.waitLeft != null) {
        waitingForLeft.put(monkey.waitLeft, monkey);
      }
      if (monkey.waitRight != null) {
        waitingForRight.put(monkey.waitRight, monkey);
      }
    }
    monkeys.forEach(System.out::println);
    System.out.println("Root monkey is at index %d".formatted(rootIndex));

    while (monkeys.get(rootIndex).result == null) {
      // replace
      monkeys.stream()
          .filter(m -> m.result != null)
          .forEach(
              monkeyWitResult -> {
                Optional.ofNullable(waitingForLeft.get(monkeyWitResult.name))
                    .ifPresent(
                        waitingMonkey -> {
                          waitingMonkey.left = monkeyWitResult.result;
                          waitingMonkey.waitLeft = null;
                          waitingForLeft.remove(monkeyWitResult.name);
                        });
                Optional.ofNullable(waitingForRight.get(monkeyWitResult.name))
                    .ifPresent(
                        waitingMonkey -> {
                          waitingMonkey.right = monkeyWitResult.result;
                          waitingMonkey.waitRight = null;
                          waitingForRight.remove(monkeyWitResult.name);
                        });
              });

      monkeys.forEach(Monkey::calculate);
    }
    System.out.println("Root monkey shouts %d".formatted(monkeys.get(rootIndex).result));
  }
}
