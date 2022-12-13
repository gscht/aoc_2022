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
    input.forEach(System.out::println);
    System.out.println("------------------------------");
    int index = 0;
    while (!input.isEmpty()) {
      index++;

      var leftInput = input.remove(0);
      var leftSide = parseLine(leftInput);
      System.out.println("%s".formatted(leftSide));

      var rightInput = input.remove(0);
      var rightSide = parseLine(rightInput);
      System.out.println("%s".formatted(rightSide));
      System.out.println();

      if (input.size() > 0) input.remove(0);
      if (index == 8) break;
    }
    System.out.println("Found %d pairs.".formatted(index));
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
