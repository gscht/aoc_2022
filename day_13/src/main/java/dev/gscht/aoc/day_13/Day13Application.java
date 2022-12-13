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
    var input = Files.readAllLines(Path.of("./input.txt"));
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
      System.out.println();

      if (input.size() > 0) input.remove(0);

      System.out.print("Checking %s vs %s".formatted(leftSide.toString(), rightSide.toString()));
      var ok = inputOk(leftSide, rightSide);
      System.out.println(": " + ok);
      if (ok) {
        sum += index;
      }
    }
    System.out.println("Sum: %d".formatted(sum));
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static boolean inputOk(List<Object> left, List<Object> right) {
    var leftInteger = left.stream().filter(o -> o instanceof Integer).count();
    var rightInteger = right.stream().filter(o -> o instanceof Integer).count();

    var allIntegers = leftInteger == left.size() && rightInteger == right.size();
    
    var size = Math.min(left.size(), right.size());
    var leftIsLarger = left.size() > right.size();
    for (int i = 0; i < size; i++) {
      var l = left.remove(0);
      var r = right.remove(0);
      if (l instanceof List && r instanceof List) {
        return inputOk((List) l, (List) r);
      } else if (l instanceof Integer && r instanceof List) {
        return inputOk(new ArrayList(List.of(l)), (List) r);
      } else if (l instanceof List && r instanceof Integer) {
        return inputOk((List) l, new ArrayList(List.of(r)));
      } else if (l instanceof Integer li && r instanceof Integer ri) {
        if (li > ri) {
          return false;
        }
      } else {
        throw new IllegalStateException(
            "Unkown left and right: " + left.getClass() + ", " + right.getClass());
      }
    }
    if (!left.isEmpty()) {
      return false;
    }
    return true;
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
