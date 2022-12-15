package dev.gscht.aoc.day_15;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
    var list = new ArrayList<Point>();
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

    System.out.println("%d/%d %d/%d".formatted(minX, minY, maxX, maxY));
    var y = 2000000;
    var counter = 0;
    for (int x = minX; x <= maxX; x++) {
      var inReach = false;
      for (var p : list) {
        if (p.inReach(x, y) && !p.isSignalAt(x, y) && !p.isBeaconAt(x, y)) {
          inReach = true;
          break;
        }
      }
      if (inReach) {
        counter++;
      }
    }
    System.out.println("%d positions where beacon cannot be.".formatted(counter));
  }
}
