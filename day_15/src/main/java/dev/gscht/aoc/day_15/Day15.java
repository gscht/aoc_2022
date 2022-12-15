package dev.gscht.aoc.day_15;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class Day15 implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {

    // Sensor at x=1518415, y=2163633: closest beacon is at x=1111304, y=1535696
    var pattern =
        Pattern.compile(
            "Sensor at x=(-?\\d*?), y=(-?\\d*?): closest beacon is at x=(-?\\d*?), y=(-?\\d*?)$");
    var lines = Files.readAllLines(Path.of("./input.txt"));
    var minX = Integer.MAX_VALUE;
    var maxX = Integer.MIN_VALUE;
    var minY = Integer.MAX_VALUE;
    var maxY = Integer.MIN_VALUE;
    List<Point> list = new ArrayList<Point>();
    for (var line : lines) {
      var matcher = pattern.matcher(line);
      matcher.find();
      var sensorX = Integer.parseInt(matcher.group(1));
      var sensorY = Integer.parseInt(matcher.group(2));
      var beaconX = Integer.parseInt(matcher.group(3));
      var beaconY = Integer.parseInt(matcher.group(4));
      // System.out.println("%d/%d -> %d/%d".formatted(sensorX, sensorY, beaconX, beaconY));

      minX = Math.min(minX, sensorX);
      minX = Math.min(minX, beaconX);
      maxX = Math.max(maxX, sensorX);
      maxX = Math.max(maxX, beaconX);

      minY = Math.min(minY, sensorY);
      minY = Math.min(minY, beaconY);
      maxY = Math.max(maxY, sensorY);
      maxY = Math.max(maxY, beaconY);
      list.add(new Point(sensorX, sensorY, beaconX, beaconY));
    }
    /*
        System.out.println("%d/%d %d/%d".formatted(minX, minY, maxX, maxY));
        var rowToInspect = 2_000_000;
        var counter = 0;
        for (int x = minX; x <= maxX; x++) {
          var inReach = false;
          for (var p : list) {
            if (p.inReach(x, rowToInspect)
                && !p.isSignalAt(x, rowToInspect)
                && !p.isBeaconAt(x, rowToInspect)) {
              inReach = true;
              break;
            }
          }
          if (inReach) {
            counter++;
          }
        }
        System.out.println("%d positions where beacon cannot be.".formatted(counter));
    */
    maxX = 20;
    maxY = 20;
    maxX = 4_000_000;
    maxY = 4_000_000;

    list = list.stream().sorted().toList();

    for (int y = 0; y <= maxY; y++) {
      int x = 0;
      while (x <= maxX) {
        var newX = 0;
        for (var point : list) {

          newX = skip(point, x, y);
          if (newX != x && newX <= maxX) {
            var covered = false;
            for (var p2 : list) {
              if (p2.contains(newX, y)) {
                covered = true;
                break;
              }
            }
            if (!covered) {
              System.out.println("%d %d".formatted(newX, y));
              System.out.println("%d".formatted(1L * newX * maxX + y));
              System.exit(0);
            }
          }
          x = newX;
        }
        if (newX > maxX)  {
          break;
        }
      }
    }

    // 1879233318 too low.
  }

  private int skip(Point p, int x, int y) {

    if (!p.contains(x, y)) {
      return x;
    }

    var dist = p.distance();
    var distY = Math.abs(p.getSignalY() - y);
    var remain = Math.abs(dist - distY);
    return p.getSignalX() + remain + 1;
  }
}
