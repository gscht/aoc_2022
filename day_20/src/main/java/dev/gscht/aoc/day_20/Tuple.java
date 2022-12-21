package dev.gscht.aoc.day_20;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class Tuple {
  long number;
  int index;

  public String toString() {
    return String.valueOf(number);
  }
}
