use std::fs;

fn main() {
    let contents =
        fs::read_to_string("./input.txt").expect("Should have been able to read the file");

    let lines: Vec<&str> = contents.split('\n').collect();
    let number_of_lines = lines.len();
    println!("{number_of_lines} lines");
}
