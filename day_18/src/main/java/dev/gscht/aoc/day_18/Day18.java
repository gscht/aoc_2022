package dev.gscht.aoc.day_18;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
class Day18 implements ApplicationRunner {

  private int minX;
  private int minY;
  private int minZ;
  private int maxX;
  private int maxY;
  private int maxZ;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    var lines = Files.readAllLines(Path.of("./input.txt"));
    List<Point> points = new ArrayList<Point>(lines.size());
    minX = Integer.MAX_VALUE;
    minY = Integer.MAX_VALUE;
    minZ = Integer.MAX_VALUE;

    maxX = Integer.MIN_VALUE;
    maxY = Integer.MIN_VALUE;
    maxZ = Integer.MIN_VALUE;

    for (var line : lines) {
      var point = new Point(line);

      minX = Math.min(minX, point.x);
      minY = Math.min(minY, point.y);
      minZ = Math.min(minZ, point.z);

      maxX = Math.max(maxX, point.x);
      maxY = Math.max(maxY, point.y);
      maxZ = Math.max(maxZ, point.z);

      points.add(point);
    }

    var totalSidesVisible = 0;
    for (var p : points) {
      var sidesVisible = 6;
      for (var n : points) {
        if (n.equals(p)) {
          continue;
        }
        if (n.touches(p)) {
          sidesVisible--;
        }
      }
      totalSidesVisible += sidesVisible;
    }
    System.out.println("There are %d sides visible".formatted(totalSidesVisible));

    var pockets = new ArrayList<Point>();
    for (var x = minX; x <= maxX; x++) {
      for (var y = minY; y <= maxY; y++) {
        for (var z = minZ; z <= maxZ; z++) {
          var pocket = new Point(x, y, z);
          if (points.contains(pocket)) {
            continue;
          }
          var c = 0;
          for (int i = 1; i <= 6; i++) {
            if (reachesRock(points, pocket, i)) {
              c++;
            }
          }
          if (c == 6 && !pockets.contains(pocket)) {
            pockets.add(pocket);
          }
        }
      }
    }

    System.out.println("Found %d pockets.".formatted(pockets.size()));

    while (!pockets.isEmpty()) {
      var pocket = pockets.remove(0);
      if (pocket.x < minX
          || pocket.x > maxX
          || pocket.y < minY
          || pocket.y > maxY
          || pocket.z < minZ
          || pocket.z > maxZ) {
        continue;
      }
      if (points.contains(pocket)) {
        continue;
      }
      if (!points.contains(pocket)) {
        points.add(pocket);
      }

      pockets.add(new Point(pocket.x - 1, pocket.y, pocket.z));
      pockets.add(new Point(pocket.x + 1, pocket.y, pocket.z));
      pockets.add(new Point(pocket.x, pocket.y - 1, pocket.z));
      pockets.add(new Point(pocket.x, pocket.y + 1, pocket.z));
      pockets.add(new Point(pocket.x, pocket.y, pocket.z - 1));
      pockets.add(new Point(pocket.x, pocket.y, pocket.z + 1));
    }

    // 3254 too high
    // 2324 too high
    totalSidesVisible = 0;
    for (var p : points) {
      var sidesVisible = 6;
      for (var n : points) {
        if (n.equals(p)) {
          continue;
        }
        if (n.touches(p)) {
          sidesVisible--;
        }
      }
      totalSidesVisible += sidesVisible;
    }
    System.out.println("There are %d sides visible".formatted(totalSidesVisible));
  }

  private boolean canReachOutside(Collection<Point> points, Point p) {
    if (points.contains(p)) {
      return false;
    }
    var seen = new HashSet<Point>();
    var stack = new Stack<Point>();
    stack.push(p);
    while (!stack.isEmpty()) {
      var pop = stack.pop();
      if (points.contains(pop)) {
        continue;
      }
      if (!(0 <= pop.x && pop.x <= 19)) {
        return true;
      }
      if (!(0 <= pop.y && pop.y <= 19)) {
        return true;
      }
      if (!(0 <= pop.z && pop.z <= 19)) {
        return true;
      }
      if (seen.contains(pop)) {
        continue;
      }
      seen.add(pop);

      stack.push(new Point(p.x - 1, p.y, p.z));
      stack.push(new Point(p.x + 1, p.y, p.z));
      stack.push(new Point(p.x, p.y - 1, p.z));
      stack.push(new Point(p.x, p.y + 1, p.z));
      stack.push(new Point(p.x, p.y, p.z - 1));
      stack.push(new Point(p.x, p.y, p.z + 1));
    }
    return false;
  }

  private boolean reachesRock(Collection<Point> points, Point p, int direction) {
    if (direction == 1) {
      for (var x = p.x - 1; x >= 0; x--) {
        var n = new Point(x, p.y, p.z);
        if (points.contains(n)) {
          return true;
        }
      }
    } else if (direction == 2) {
      for (var x = p.x + 1; x <= maxX; x++) {
        var n = new Point(x, p.y, p.z);
        if (points.contains(n)) {
          return true;
        }
      }
    } else if (direction == 3) {
      for (var y = p.y - 1; y >= 0; y--) {
        var n = new Point(p.x, y, p.z);
        if (points.contains(n)) {
          return true;
        }
      }
    } else if (direction == 4) {
      for (var y = p.y + 1; y <= maxY; y++) {
        var n = new Point(p.x, y, p.z);
        if (points.contains(n)) {
          return true;
        }
      }
    } else if (direction == 5) {
      for (var z = p.z - 1; z >= 0; z--) {
        var n = new Point(p.x, p.y, z);
        if (points.contains(n)) {
          return true;
        }
      }
    } else if (direction == 6) {
      for (var z = p.z + 1; z <= maxZ; z++) {
        var n = new Point(p.x, p.y, z);
        if (points.contains(n)) {
          return true;
        }
      }
    }

    return false;
  }
}
