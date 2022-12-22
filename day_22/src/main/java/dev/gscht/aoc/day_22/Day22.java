package dev.gscht.aoc.day_22;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
class Day22 implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    var input = Files.readAllLines(Path.of("input.txt"));
    var lines = new ArrayList<String>();
    var directions = "";
    var mapDone = false;
    for (var line : input) {
      if (line.isEmpty()) {
        mapDone = true;
        continue;
      }
      if (!mapDone) {
        lines.add(line);
      } else {
        directions = line;
      }
    }
    var pattern = Pattern.compile("(\\d{1,3})([LR])");
    var matcher = pattern.matcher(directions);
    var rowIndex = 0;
    var colIndex = lines.get(0).indexOf('.');
    var direction = Direction.right;
    while (matcher.find()) {
      var steps = Integer.parseInt(matcher.group(1));
      var turning = matcher.group(2);
      System.out.println("%d%s".formatted(steps, turning));
      var currentLine = lines.get(rowIndex);
      while (steps > 0) {
        var newColIndex = colIndex;
        var newRowIndex = rowIndex;
        var newLine = lines.get(newRowIndex);
        switch (direction) {
          case right -> {
            newColIndex = colIndex + 1;
            if (newColIndex == currentLine.length() || currentLine.charAt(newColIndex) == ' ') {
              newColIndex = indexOfFirstSymbolInLine(currentLine);
            }
          }
          case left -> {
            newColIndex = colIndex - 1;
            if (newColIndex == -1 || currentLine.charAt(newColIndex) == ' ') {
              newColIndex = currentLine.length() - 1;
            }
          }
          case up -> {
            newRowIndex = rowIndex - 1;
            if (newRowIndex == -1) {
              newRowIndex = lines.size() - 1;
            }
            newLine = lines.get(newRowIndex);

            while (!(newColIndex < newLine.length() && newLine.charAt(newColIndex) != ' ')) {
              newRowIndex--;
              if (newRowIndex == -1) {
                newRowIndex = lines.size() - 1;
              }
              newLine = lines.get(newRowIndex);
            }
          }
          case down -> {
            newRowIndex = rowIndex + 1;
            if (newRowIndex == lines.size()) {
              newRowIndex = 0;
            }
            newLine = lines.get(newRowIndex);
            while (!(newColIndex < newLine.length() && newLine.charAt(newColIndex) != ' ')) {
              newRowIndex++;
              if (newRowIndex == lines.size()) {
                newRowIndex = 0;
              }
              newLine = lines.get(newRowIndex);
            }
          }
        }
        var nextSymbol = newLine.charAt(newColIndex);
        if (nextSymbol == '#') {
          break;
        }
        currentLine = newLine;
        colIndex = newColIndex;
        rowIndex = newRowIndex;
        steps--;
      }
      // Time to turn
      switch (turning) {
        case "L" -> direction = rotateL(direction);
        case "R" -> direction = rotateR(direction);
      }
      System.out.println("Now at %d %d facing %s".formatted(rowIndex, colIndex, direction));
    }
    System.out.println("Stopped at %d %d facing %s".formatted(rowIndex, colIndex, direction));

    long ans = (rowIndex + 1L) * 1000 + (colIndex + 1) * 4 + direction.val;
    System.out.println("%d".formatted(ans));
    // 123106 too hight
  }

  private int indexOfFirstSymbolInLine(String line) {

    var firstTile = line.indexOf('.');
    var firstWall = line.indexOf('#');
    return firstTile < firstWall ? firstTile : firstWall;
  }

  private Direction rotateL(Direction d) {
    return switch (d) {
      case left -> {
        yield Direction.down;
      }
      case right -> {
        yield Direction.up;
      }
      case up -> {
        yield Direction.left;
      }
      case down -> {
        yield Direction.right;
      }
    };
  }

  private Direction rotateR(Direction d) {
    return switch (d) {
      case left -> {
        yield Direction.up;
      }
      case right -> {
        yield Direction.down;
      }
      case up -> {
        yield Direction.right;
      }
      case down -> {
        yield Direction.left;
      }
    };
  }

  enum Direction {
    right(0),
    down(1),
    left(2),
    up(3);

    int val;

    Direction(int v) {
      this.val = v;
    }
  }
}
