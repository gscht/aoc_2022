package dev.gscht.aoc.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day14Application {

  public static void main(String[] args) throws IOException {
    var lines = Files.readAllLines(Path.of("./input.txt"));
    int rows = 0;
    int cols = 0;
    for (var line : lines) {
      var points = line.split(" -> ");
      for (var point : points) {
        var col = Integer.parseInt(point.split(",")[0]);
        var row = Integer.parseInt(point.split(",")[1]);
        rows = Math.max(rows, row);
        cols = Math.max(cols, col);
      }
    }
    
    rows += 2;
    cols += 100_000;

    var cave = new char[rows + 1][cols + 1];
    for (var row = 0; row < cave.length; row++) {
      for (var col = 0; col < cave[0].length; col++) {
        cave[row][col] = '.';
      }
    }

    for (var line : lines) {
      var fromRow = -1;
      var fromCol = -1;
      var toRow = -1;
      var toCol = -1;
      var points = line.split(" -> ");
      for (var point : points) {
        toCol = Integer.parseInt(point.split(",")[0]);
        toRow = Integer.parseInt(point.split(",")[1]);
        if (fromRow != -1 && toRow != -1 && fromCol != -1 && toCol != -1) {
          cave[fromRow][fromCol] = '#';
          if (fromRow == toRow) {
            while (fromCol != toCol) {
              if (fromCol < toCol) {
                fromCol++;
              } else {
                fromCol--;
              }
              cave[fromRow][fromCol] = '#';
            }
          } else if (fromCol == toCol) {
            while (fromRow != toRow) {
              if (fromRow < toRow) {
                fromRow++;
              }else {
                fromRow--;
              }
              cave[fromRow][fromCol] = '#';
            }
          } else {
            throw new IllegalArgumentException();
          }
        }
        fromRow = toRow;
        fromCol = toCol;
      }
    }

    for (var c = 0; c <= cols; c++) {
      cave[rows][c] = '#';
    }

/*
    for (var r = 0; r <= rows; r++) {
      for (var c = 490; c <= 510; c++) {
        System.out.print(cave[r][c]);
      }
      System.out.println();
    }
*/

    int sandCount = 0;
    int finalRow = 0;
    do {
      finalRow = 0;
      sandCount++;
      finalRow = dropSand(0, 500, cave);
    } while (finalRow != -1);

    System.out.println("%d sands dropped.".formatted(sandCount));
  }

  private static int dropSand(int row, int col, char[][] cave) {
    if (row == cave.length-1) {
      return -1;
    }
    // Check down
    if (cave[row + 1][col] == '.') {
      row++;
      return dropSand(row, col, cave);
    } else if (col - 1 >= 0 && cave[row + 1][col - 1] == '.') {
      row++;
      col--;
      return dropSand(row, col, cave);
    } else if (col + 1 < cave[0].length && cave[row + 1][col + 1] == '.') {
      row++;
      col++;
      return dropSand(row, col, cave);
    }
    cave[row][col] = 'o';
    if (row == 0 && col == 500) {
      return -1;
    }
    return row;
  }
}
