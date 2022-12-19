package dev.gscht.aoc.day_18;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"x", "y", "z"})
@AllArgsConstructor
class Point {
  int x;
  int y;
  int z;

  public Point(String s) {
    var coordinates = s.split(",");
    x = Integer.parseInt(coordinates[0]);
    y = Integer.parseInt(coordinates[1]);
    z = Integer.parseInt(coordinates[2]);
  }

  public boolean touches(Point p) {
    var xTouching = Math.abs(x - p.x) == 1 && y == p.y && z == p.z ? 1 : 0;
    var yTouching = Math.abs(y - p.y) == 1 && x == p.x && z == p.z ? 1 : 0;
    var zTouching = Math.abs(z - p.z) == 1 && x == p.x && y == p.y ? 1 : 0;
    return xTouching > 0 || yTouching > 0 || zTouching > 0;
  }

  public Point(Point p) {
    this.x = p.x;
    this.y = p.y;
    this.z = p.z;
  }

  public Point left(){
    x--;
    return this;
  }
  public Point right() {
    x++;
    return this;
  }
  public Point up() {
    y++;
    return this;
    
  }
  
  public Point down() {
    y--;
    return this;
  }

  public Point backwards() {
    z++;
    return this;
  }

  public Point forward() {
    z--;
    return this;
  }

  public String toString() {
    return x + "/" + y + "/" + z;
  }
}
