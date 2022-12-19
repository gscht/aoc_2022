package dev.gscht.aoc.day_18;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
class Day18 implements ApplicationRunner {

  private final List<Point> points = new ArrayList<>();
  private int minX;
  private int minY;
  private int minZ;
  private int maxX;
  private int maxY;
  private int maxZ;

  private final Map<Point, Boolean> pointsCache = new HashMap<>();

  @Override
  public void run(ApplicationArguments args) throws Exception {
    var lines = Files.readAllLines(Path.of("./input.txt"));
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
    System.out.println("%d/%d/%d %d/%d/%d".formatted(minX, minY, minZ, maxX, maxY, maxZ));

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

    var visible = 0;
    var numberOfPoints = points.size();
    for (var p : points) {
      var check = new Point(p).left();
      var result = expose(check);
      pointsCache.put(check, result);
      if (result) {
        visible++;
      }

      check = new Point(p).right();
      result = expose(check);
      pointsCache.put(check, result);
      if (result) {
        visible++;
      }

      check = new Point(p).up();
      result = expose(check);
      pointsCache.put(check, result);
      if (result) {
        visible++;
      }
      check = new Point(p).down();
      result = expose(check);
      pointsCache.put(check, result);
      if (result) {
        visible++;
      }
      check = new Point(p).backwards();
      result = expose(check);
      pointsCache.put(check, result);
      if (result) {
        visible++;
      }
      check = new Point(p).forward();
      result = expose(check);
      pointsCache.put(check, result);
      if (result) {
        visible++;
      }
      numberOfPoints--;
      System.out.println("%d points left".formatted(numberOfPoints));
    }

    System.out.println("There are %d sides visible".formatted(visible));
  }

  private boolean expose(Point p) {
    Boolean result = pointsCache.get(p);
    if (result != null) {
      return result;
    }
    if (points.contains(p)) {
      return false;
    }

    var stack = new Stack<Point>();
    stack.push(p);
    var seen = new HashSet<String>();

    while (!stack.isEmpty()) {
      var pop = stack.pop();
      var x = pop.x;
      var y = pop.y;
      var z = pop.z;
      if (points.contains(pop)) {
        continue;
      }
      if (x <= minX || x >= maxX || y <= minY || y >= maxY || z <= minZ || z >= maxZ) {
        return true;
      }
      if (seen.contains(pop.toString())) {
        continue;
      }
      seen.add(pop.toString());
      stack.push(new Point(pop).left());
      stack.push(new Point(pop).right());
      stack.push(new Point(pop).up());
      stack.push(new Point(pop).down());
      stack.push(new Point(pop).backwards());
      stack.push(new Point(pop).forward());
    }
    return false;
  }
}
