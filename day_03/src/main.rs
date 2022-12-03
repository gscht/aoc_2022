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
    sum = 0;
    let number_of_lines = lines.len() - 1;
    let mut lines_consumed = 0;
    while lines_consumed < number_of_lines {
        let group_of_rucksacks = lines.get(lines_consumed..lines_consumed+3).unwrap();
        let rucksack_1: HashSet<char> = group_of_rucksacks[0].chars().collect();
        let rucksack_2: HashSet<char> = group_of_rucksacks[1].chars().collect();
        let rucksack_3: HashSet<char> = group_of_rucksacks[2].chars().collect();
        let intersection: HashSet<_> = rucksack_1.intersection(&rucksack_2).copied().collect();
        let intersection1: HashSet<_> = intersection.intersection(&rucksack_3).copied().collect();
        let found = intersection1.iter().next().unwrap();
        lines_consumed += 3;
        if found.is_uppercase() {
            let upper_case_letter_number = *found as u32;
            sum += upper_case_letter_number - 38;
        } else {
            let lower_case_letter_number = *found as u32;
            sum += lower_case_letter_number - 96;
        }
    }
    println!("{sum}");
}
