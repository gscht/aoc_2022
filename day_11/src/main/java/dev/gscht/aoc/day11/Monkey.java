package dev.gscht.aoc.day11;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
class Monkey implements Comparable<Monkey> {
  private List<BigInteger> items = new ArrayList<>();
  private int test;
  private int trueToMonkey;
  private int falseToMonkey;
  private long inspectedItems;

  public void inspectItem() {
    inspectedItems++;
  }

  @Override
  public int compareTo(Monkey o) {
    return Long.compare(inspectedItems, o.inspectedItems);
  }
}
