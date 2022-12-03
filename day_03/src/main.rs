use std::collections::HashSet;
use std::fs;
fn main() {
    let contents =
        fs::read_to_string("./input.txt").expect("Should have been able to read the file");

    let lines: Vec<&str> = contents.split('\n').collect();
    let mut sum = 0;
    for line in &lines {
        if line == &"" {
            continue;
        }
        let number_of_items = line.len() / 2;
        let (compartment_1, compartment_2) = line.split_at(number_of_items);

        let compartment_1: HashSet<char> = compartment_1.chars().collect();
        let compartment_2: HashSet<char> = compartment_2.chars().collect();

        let intersection: HashSet<_> = compartment_1.intersection(&compartment_2).collect();
        let found = intersection.iter().next().unwrap();
        if found.is_uppercase() {
            let upper_case_letter_number = **found as u32;
            sum += upper_case_letter_number - 38;
        } else {
            let lower_case_letter_number = **found as u32;
            sum += lower_case_letter_number - 96;
        }
    }
    println!("{sum}");
}
