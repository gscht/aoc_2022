package dev.gscht.aoc.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day12Application {

  public static void main(String[] args) throws IOException {

    var input = Files.readAllLines(Path.of("./input.txt"));

    var map = new char[input.size()][input.get(0).length()];

    var startRow = -1;
    var startCol = -1;

    var targetRow = -1;
    var targetCol = -1;

    var possibleStarts = new ArrayList<Field>();

    for (var row = 0; row < input.size(); row++) {
      var line = input.get(row);
      for (var col = 0; col < line.length(); col++) {
        if (row == 0 && line.charAt(col) == 'a') {
          possibleStarts.add(new Field(row, col, 0, ""));
        }
        if (col == 0 && line.charAt(col) == 'a') {
          possibleStarts.add(new Field(row, col, 0, ""));
        }
        map[row][col] = line.charAt(col);
        if (line.charAt(col) == 'S') {
          startCol = col;
        } else if (line.charAt(col) == 'E') {
          targetCol = col;
        }
      }
      if (startRow == -1 && startCol != -1) {
        startRow = row;
      }
      if (targetRow == -1 && targetCol != -1) {
        targetRow = row;
      }
    }
    map[targetRow][targetCol] = 'z' + 1;

    var minPath = Integer.MAX_VALUE;

    System.out.println("Found %d possible starts".formatted(possibleStarts.size()));
    for (var i = 0; i < possibleStarts.size(); i++) {
      System.out.println("Checking start %d".formatted(i + 1));
      var start = possibleStarts.get(i);
      var pathLength = countPathLength(map, start) - 1;
      if (pathLength >= 0) {
        minPath = Math.min(minPath, pathLength);
      }
    }
    System.out.println("Min path: %d".formatted(minPath));

    SpringApplication.run(Day12Application.class, args);
  }

  private static int countPathLength(char[][] map, Field start) {
    var visited = new boolean[map.length][map[0].length];
    var startRow = start.row;
    var startCol = start.col;
    map[startRow][startCol] = 'a' - 1;
    visited[startRow][startCol] = true;
    var queue = new LinkedList<Field>();
    queue.add(new Field(startRow, startCol, 0, ""));
    while (!queue.isEmpty()) {
      var nextField = queue.remove();
      int row = nextField.row;
      int col = nextField.col;
      int dist = nextField.distance;

      // Up
      if (isValid(row - 1, col, map[row][col], map, visited)) {
        queue.add(new Field(row - 1, col, dist + 1, nextField.moves + "^"));
        visited[row - 1][col] = true;
      }
      // Down
      if (isValid(row + 1, col, map[row][col], map, visited)) {
        queue.add(new Field(row + 1, col, dist + 1, nextField.moves + "V"));
        visited[row + 1][col] = true;
      }
      // Left
      if (isValid(row, col - 1, map[row][col], map, visited)) {
        queue.add(new Field(row, col - 1, dist + 1, nextField.moves + "<"));
        visited[row][col - 1] = true;
      }
      // Right
      if (isValid(row, col + 1, map[row][col], map, visited)) {
        queue.add(new Field(row, col + 1, dist + 1, nextField.moves + ">"));
        visited[row][col + 1] = true;
      }
      if (map[row][col] == 'z' + 1) {
        map[startRow][startCol] = 'a';
        return nextField.distance;
      }
    }
    map[startRow][startCol] = 'a';
    return -1;
  }

  private static void printMap(char[][] map) {
    for (var row = 0; row < map.length; row++) {
      for (var col = 0; col < map[0].length; col++) {
        System.out.print(map[row][col]);
      }
      System.out.println();
    }
  }

  @AllArgsConstructor
  private static final class Field {
    public int row;
    public int col;
    public int distance;
    public String moves = "";
  }

  private static boolean isValid(
      int row, int col, char currentHeight, char[][] map, boolean[][] visited) {
    if (row >= 0 && col >= 0 && row < map.length && col < map[0].length && !visited[row][col]) {
      var distance = map[row][col] - currentHeight;
      var moveOk = distance <= 1;
      // System.out.println( "From %s to %s possible: %s".formatted(currentHeight, map[row][col],
      // moveOk));
      return moveOk;
    }
    return false;
  }
}
