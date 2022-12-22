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
    var pattern = Pattern.compile("(\\d{1,3})([LR]?)");
    var matcher = pattern.matcher(directions);
    var rowIndex = 0;
    var colIndex = lines.get(0).indexOf('.');
    var direction = Direction.right;
    while (matcher.find()) {
      var steps = Integer.parseInt(matcher.group(1));
      var turning = matcher.group(2);
      System.out.println("%d%s".formatted(steps, turning));
      while (steps > 0) {
        var side = getSide(rowIndex, colIndex);
        var newColIndex = colIndex;
        var newRowIndex = rowIndex;
        var line = lines.get(newRowIndex);
        switch (direction) {
          case right -> {
            newColIndex = colIndex + 1;
            if (newColIndex == line.length()) {
              if (side == 2) {
                newRowIndex = 100 + (49 - rowIndex);
                newColIndex = 99;
              } else if (side == 3) {
                newRowIndex = 49;
                newColIndex = 100 + (rowIndex - 50);
              } else if (side == 4) {
                newRowIndex = 49 - (rowIndex - 100);
                newColIndex = 149;
              } else if (side == 6) {
                newRowIndex = 149;
                newColIndex = 50 + (rowIndex - 150);
              }
            }
          }
          case left -> {
            newColIndex = colIndex - 1;
            if (newColIndex == -1 || line.charAt(newColIndex) == ' ') {
              if (side == 1) {
                newRowIndex = 149 - rowIndex;
                newColIndex = 0;
              } else if (side == 3) {
                newRowIndex = 100;
                newColIndex = rowIndex - 50;
              } else if (side == 5) {
                newRowIndex = 49 - (rowIndex - 100);
                newColIndex = 50;
              } else if (side == 6) {
                newRowIndex = 0;
                newColIndex = 50 + (rowIndex - 150);
              }
            }
          }
          case up -> {
            newRowIndex = rowIndex - 1;
            if (side == 1 && newRowIndex == -1) {
              newRowIndex = 150 + colIndex - 50;
              newColIndex = 0;
            } else if (side == 2 && newRowIndex == -1) {
              newRowIndex = 199;
              newColIndex -= 100;
            } else if (side == 5 && newRowIndex == 99) {
              newRowIndex = 50 + colIndex;
              newColIndex = 50;
            }
          }
          case down -> {
            newRowIndex = rowIndex + 1;
            if (side == 2 && newRowIndex == 50) {
              newRowIndex = 50 + (colIndex - 100);
              newColIndex = 99;
            } else if (side == 4 && newRowIndex == 150) {
              newRowIndex = 150 + (colIndex - 50);
              newColIndex = 49;
            } else if (side == 6 && newRowIndex == 200) {
              newRowIndex = 0;
              newColIndex = colIndex + 100;
            }
          }
        }
        line = lines.get(newRowIndex);
        var nextSymbol = line.charAt(newColIndex);
        if (nextSymbol == '#') {
          break;
        }
        var newSide = getSide(newRowIndex, newColIndex);
        if (newSide != side) {
          direction = correctDirection(side, newSide);
          System.out.println(
              "Changed side from %d to %d. New direction is %s. We are at %d %d. %d steps left"
                  .formatted(side, newSide, direction, newRowIndex, newColIndex, steps - 1));
          if (newRowIndex == 0
              || newColIndex == 0
              || newRowIndex % 50 == 0
              || newColIndex % 50 == 0
              || (newRowIndex + 1) % 50 == 0
              || (newColIndex + 1) % 50 == 0) {
          } else {
            throw new IllegalStateException();
          }
        }
        colIndex = newColIndex;
        rowIndex = newRowIndex;
        steps--;
      }
      // Time to turn
      switch (turning) {
        case "L" -> direction = rotateL(direction);
        case "R" -> direction = rotateR(direction);
        default -> {}
      }
    }
    System.out.println("Stopped at %d %d facing %s".formatted(rowIndex, colIndex, direction));

    long ans = (rowIndex + 1L) * 1000 + (colIndex + 1) * 4 + direction.val;
    System.out.println("%d".formatted(ans));
    // 123106 too hight
    //
    //
    // Part 2:
    // 148204 too low
    // 129347
  }

  private Direction correctDirection(int oldSide, int newSide) {
    if (oldSide == 1) {
      if (newSide == 2) {
        return Direction.right;
      }
      if (newSide == 3) {
        return Direction.down;
      }
      if (newSide == 5) {
        return Direction.right;
      }
      if (newSide == 6) {
        return Direction.right;
      }
    }

    if (oldSide == 2) {
      if (newSide == 1) {
        return Direction.left;
      }
      if (newSide == 3) {
        return Direction.left;
      }
      if (newSide == 4) {
        return Direction.left;
      }
      if (newSide == 6) {
        return Direction.up;
      }
    }

    if (oldSide == 3) {
      if (newSide == 1) {
        return Direction.up;
      }
      if (newSide == 2) {
        return Direction.up;
      }
      if (newSide == 4) {
        return Direction.down;
      }
      if (newSide == 5) {
        return Direction.down;
      }
    }

    if (oldSide == 4) {
      if (newSide == 2) {
        return Direction.left;
      }
      if (newSide == 3) {
        return Direction.up;
      }
      if (newSide == 5) {
        return Direction.left;
      }
      if (newSide == 6) {
        return Direction.left;
      }
    }

    if (oldSide == 5) {
      if (newSide == 1) {
        return Direction.right;
      }
      if (newSide == 3) {
        return Direction.right;
      }
      if (newSide == 4) {
        return Direction.right;
      }
      if (newSide == 6) {
        return Direction.down;
      }
    }

    if (oldSide == 6) {
      if (newSide == 1) {
        return Direction.down;
      }
      if (newSide == 2) {
        return Direction.down;
      }
      if (newSide == 4) {
        return Direction.up;
      }
      if (newSide == 5) {
        return Direction.up;
      }
    }
    throw new IllegalStateException(
        "Side change from %d to %d is not possible".formatted(oldSide, newSide));
  }

  private int getSide(int row, int col) {
    if (row >= 0 && row <= 49 && col >= 50 && col <= 99) {
      return 1;
    }
    if (row >= 0 && row <= 49 && col >= 100 && col <= 149) {
      return 2;
    }
    if (row >= 50 && row <= 99 && col >= 50 && col <= 99) {
      return 3;
    }
    if (row >= 100 && row <= 149 && col >= 0 && col <= 49) {
      return 5;
    }
    if (row >= 100 && row <= 149 && col >= 50 && col <= 149) {
      return 4;
    }
    if (row >= 150 && row <= 199 && col >= 0 && col <= 49) {
      return 6;
    }
    throw new IllegalStateException("%d %d unknown side".formatted(row, col));
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
