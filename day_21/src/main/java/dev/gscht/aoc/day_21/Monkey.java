package dev.gscht.aoc.day_21;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(of = "name")
class Monkey {
  String name;
  Long result;
  Long left;
  Long right;
  String operation;
  String waitLeft;
  String waitRight;
  boolean match = false;

  public Monkey(String input) {
    name = input.split(": ")[0];
    var rightSide = input.split(": ")[1];
    try {
      result = Long.parseLong(rightSide);
    } catch (Exception ex) {
      waitLeft = rightSide.substring(0, 4);
      operation = rightSide.substring(5, 6);
      waitRight = rightSide.substring(7);
    }
  }

  public void calculate() {
    if (result == null && left != null && right != null) {
      if (waitLeft != null || waitRight != null) {
        throw new IllegalStateException();
      }
      if (operation == "=") {
        match = left == right;
      } else {
        result =
            switch (operation) {
              case "+" -> left + right;
              case "-" -> left - right;
              case "*" -> left * right;
              case "/" -> left / right;
              default -> throw new IllegalStateException();
            };
      }
    }
  }
}
