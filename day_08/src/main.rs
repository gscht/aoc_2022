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
}
