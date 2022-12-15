package dev.gscht.aoc.day_15;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point {
  private int signalX;
  private int signalY;
  private int beaconX;
  private int beaconY;
  private int distance;

  public Point(int sx, int sy, int bx, int by) {
    signalX = sx;
    signalY = sy;
    beaconX = bx;
    beaconY = by;
    distance = distance();
  }


  public String toString() {
    return "%d/%d %d/%d (%d)".formatted(signalX, signalY, beaconX, beaconY, distance());
  }


  public int distance() {
    return distance(signalX, signalY, beaconX, beaconY);
  }

  public boolean inReach(int x, int y) {
    return distance(x, y, signalX, signalY) <= distance;
  }

  public boolean inReachOfBeacon(int x, int y) {
    return distance(x, y, beaconX, beaconY) <= distance;
  }

  public boolean isSignalAt(int x, int y) {
    return signalX == x && signalY == y;
  }

  public boolean isBeaconAt(int x, int y) {
    return beaconX == x && beaconY == y;
  }

  private int distance(int x1, int y1, int x2, int y2) {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
  }
}
