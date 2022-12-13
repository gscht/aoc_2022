package dev.gscht.aoc.day_13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day13Application {

  public static void main(String[] args) throws IOException {
    var input = Files.readAllLines(Path.of("./input-test.txt"));
    // input.forEach(System.out::println);
    // System.out.println("------------------------------");
    int index = 0;
    int sum = 0;
    while (!input.isEmpty()) {
      index++;

      var leftInput = input.remove(0);
      var leftSide = parseLine(leftInput);
      // System.out.println("%s".formatted(leftSide));

      var rightInput = input.remove(0);
      var rightSide = parseLine(rightInput);
      // System.out.println("%s".formatted(rightSide));

      if (input.size() > 0) {
        input.remove(0);
      }

      var ok = compare(leftSide, rightSide);
      if (ok == 1) {
        sum += index;
      }
    }
    System.out.println("Sum: %d".formatted(sum));

    input = Files.readAllLines(Path.of("./input-test.txt"));

    input.add("[[2]]");
    input.add("[[6]]");
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static int compare(Object left, Object right) {
    if (left instanceof Integer l && right instanceof Integer r) {
      if (l == r) {
        return 0;
      }
      if (l < r) {
        return 1;
      }
      return -1;
    }

    if (left instanceof Integer && right instanceof List) {
      left = new ArrayList(List.of(left));
    } else if (left instanceof List && right instanceof Integer) {
      right = new ArrayList(List.of(right));
    }

    if (left instanceof List l && right instanceof List r) {
      var size = Math.min(l.size(), r.size());
      for (var i = 0; i < size; i++) {
        var result = compare(l.get(i), r.get(i));
        if (result != 0) {
          return result;
        }
      }

      if (size == l.size()) {
        if (l.size() == r.size()) {
          return 0;
        }
        return 1;
      }
      return -1;
    }
    throw new IllegalStateException();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static List<Object> parseLine(String line) {
    Stack s = new Stack();
    var number = "";
    List<Object> outerList = null;
    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      switch (c) {
        case '[' -> {
          var newList = new ArrayList<Object>();
          if (outerList == null) {
            outerList = newList;
          }
          if (s.size() > 0) {
            ((List<Object>) s.peek()).add(newList);
          }
          s.push(newList);
        }
        case ']' -> {
          if (number.length() > 0) {
            ((List<Object>) s.peek()).add(Integer.parseInt(number));
            number = "";
          }
          s.pop();
        }
        case ',' -> {
          if (number.length() > 0) {
            ((List<Object>) s.peek()).add(Integer.parseInt(number));
            number = "";
          }
        }
        default -> number += c;
      }
    }
    return outerList;
  }
}
