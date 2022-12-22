package dev.gscht.aoc.day_20;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
class Day20 implements ApplicationRunner {

  void solve(List<String> input) {
    List<Num> file = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) file.add(new Num(input.get(i), i));

    List<Num> output = mix(file, file);
    var x = getAnswer(output);
    System.out.println("%d".formatted(x));

    for (Num num : file) num.value *= 811589153;

    output = new ArrayList<>(file);
    for (int i = 0; i < 10; i++) output = mix(file, output);
    x = getAnswer(output);
    System.out.println("%d".formatted(x));
  }

  public long getAnswer(List<Num> out) {
    int zeroIndex = 0;
    for (int i = 0; i < out.size(); i++) {
      if (out.get(i).value == 0) {
        zeroIndex = i;
        break;
      }
    }

    int thousandth = (zeroIndex + 1000) % out.size();
    int twoThousandth = (zeroIndex + 2000) % out.size();
    int threeThousandth = (zeroIndex + 3000) % out.size();
    return out.get(thousandth).value
        + out.get(twoThousandth).value
        + out.get(threeThousandth).value;
  }

  public List<Num> mix(List<Num> ogFile, List<Num> toMix) {
    List<Num> output = new ArrayList<>(toMix);

    for (Num num : ogFile) {
      int ogIndex = output.indexOf(num);
      output.remove(num);
      long newIndex = (ogIndex + num.value) % output.size();
      if (newIndex < 0) newIndex = newIndex + output.size();
      output.add((int) newIndex, num);
    }
    return output;
  }

  private static class Num {
    public int index;
    public long value;

    public Num(String value, int index) {
      this.value = Long.parseLong(value);
      this.index = index;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Num num = (Num) o;
      return index == num.index && value == num.value;
    }

    @Override
    public int hashCode() {
      return Objects.hash(index, value);
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    solve(Files.readAllLines(Path.of("./input.txt")));

    var index = new AtomicInteger();
    List<Tuple> initialNumbers =
        Files.readAllLines(Path.of("./input.txt")).stream()
            .map(Long::parseLong)
            //        .map(n -> n * 811589153)
            .map(number -> new Tuple(number, index.getAndIncrement()))
            .collect(Collectors.toList());

    // System.out.println("%s".formatted(initialNumbers));
    for (int i = 0; i < initialNumbers.size(); i++) {
      var actualIndex = 0;
      for (int j = 0; j < initialNumbers.size(); j++) {
        if (initialNumbers.get(j).index == i) {
          actualIndex = j;
          break;
        }
      }
      var rotate = initialNumbers.get(actualIndex).number;
      if (rotate < 0) {
        rotate = initialNumbers.size() + rotate - 1;
      }

      rotate(initialNumbers, actualIndex, rotate);
    }
    var indexOfZero = -1;
    for (int i = 0; i < initialNumbers.size(); i++) {
      if (initialNumbers.get(i).number == 0) {
        indexOfZero = i;
        break;
      }
    }
    // System.out.println("%s".formatted(initialNumbers));

    var ans = 0;
    var offset = indexOfZero + 1000;
    if (offset > initialNumbers.size()) {
      offset = initialNumbers.size() - 1000 % initialNumbers.size();
      ans += initialNumbers.get(indexOfZero - offset).number;
    } else {
      ans += initialNumbers.get(indexOfZero + 1000).number;
    }

    offset = indexOfZero + 2000;
    if (offset > initialNumbers.size()) {
      offset = initialNumbers.size() - 2000 % initialNumbers.size();
      ans += initialNumbers.get(indexOfZero - offset).number;
    } else {
      ans += initialNumbers.get(indexOfZero + 2000).number;
    }

    offset = indexOfZero + 3000;
    if (offset > initialNumbers.size()) {
      offset = initialNumbers.size() - 3000 % initialNumbers.size();
      ans += initialNumbers.get(indexOfZero - offset).number;
    } else {
      ans += initialNumbers.get(indexOfZero + 3000).number;
    }

    // -5336 wrong
    System.out.println("Answer: %d".formatted(ans));
  }

  public void rotate(List<Tuple> list, int start, long amount) {
    var startAmount = amount;
    if (start < 0) {
      throw new IllegalStateException();
    }
    if (amount % list.size() == 0) {
      return;
    }
    if (amount == 0) {
      return;
    }

    if (Math.abs(amount) > list.size()) {
      amount = amount % list.size();
    }
    var overflow = false;
    if (start + amount + 1 >= list.size() || start + amount < 0) {
      overflow = true;
    }
    if (amount > 0 && overflow) {
      amount = list.size() - amount - 1;
      amount *= -1;
    } else if (amount < 0 && overflow) {
      amount = list.size() + amount - 1;
    }
    if (amount > 0) {
      for (int i = start; i < start + amount; i++) {
        Collections.swap(list, i, i + 1);
      }
    } else {
      for (int i = start; i > start + amount; i--) {
        Collections.swap(list, i, i - 1);
      }
    }
  }
}
