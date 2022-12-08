use ndarray::Array2;
use std::fs;

fn main() {
    let mut trees = Array2::<u32>::zeros((99, 99));
    // Set the value at index (1, 2) to 10
    //trees[(1, 2)] = 10;
    let contents =
        fs::read_to_string("./input.txt").expect("Should have been able to read the file");
    let lines: Vec<&str> = contents.split('\n').filter(|line| line.len() > 0).collect();
    println!("Read {} lines", lines.len());

    for (i, line) in lines.iter().enumerate() {
        for (j, tree_height) in line.chars().enumerate() {
            trees[(i, j)] = tree_height.to_digit(10).unwrap();
        }
    }
    let mut visible_trees = 0;

    for row in 1..=97 {
        for col in 1..=97 {
            let current_tree_height = trees[(row, col)];

            let mut visible_from_top = true;
            let mut visible_from_bottom = true;
            let mut visible_from_left = true;
            let mut visible_from_right = true;
            // look up
            for up in 0..row {
                if trees[(up, col)] >= current_tree_height {
                    visible_from_top = false;
                    break;
                }
            }
            // look down
            for down in row + 1..=98 {
                if trees[(down, col)] >= current_tree_height {
                    visible_from_bottom = false;
                    break;
                }
            }
            // look left
            for left in 0..col {
                if trees[(row, left)] >= current_tree_height {
                    visible_from_left = false;
                    break;
                }
            }
            // look right
            for right in col + 1..=98 {
                if trees[(row, right)] >= current_tree_height {
                    visible_from_right = false;
                    break;
                }
            }

            if visible_from_top || visible_from_bottom || visible_from_left || visible_from_right {
                visible_trees += 1;
            }
        }
    }

    let total_visible = 98 * 4 + visible_trees;

    println!("Trees visible: {total_visible}");
    // Part 2
    let mut max_view = 0;
    for row in 1..=97 {
        for col in 1..=97 {
            let current_tree_height = trees[(row, col)];
            let mut counter = 0;

            // look up
            for up in (0..=row-1).rev() {
                counter += 1;
                if trees[(up, col)] >= current_tree_height {
                    break;
                }
            }
            let view_top = counter;

            counter = 0;
            // look down
            for down in row + 1..=98 {
                counter += 1;
                if trees[(down, col)] >= current_tree_height {
                    break;
                }
            }
            let view_bottom = counter;

            counter = 0;
            // look left
            for left in (0..=col-1).rev() {
                counter += 1;
                if trees[(row, left)] >= current_tree_height {
                    break;
                }
            }
            let view_left = counter;

            counter = 0;
            // look right
            for right in col + 1..=98 {
                counter += 1;
                if trees[(row, right)] >= current_tree_height {
                    break;
                }
            }
            let view_right = counter;

            let current_view = view_top * view_bottom * view_left * view_right;
            if current_view > max_view {
                println!(
                    "To top {}, to bottom {}, to left {}, to right {}",
                    view_top, view_bottom, view_left, view_right
                );
                println!("Tree coordinates: {}/{}", row+1, col+1);
                max_view = current_view;
                println!("New max found: {max_view}");
            }
        }
    }
    println!("Max view is {max_view}");
}
