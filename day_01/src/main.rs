use std::fs;

fn main() {
    let contents =
        fs::read_to_string("./input.txt").expect("Could not read input.txt");

    let lines: Vec<&str> = contents.split('\n').collect();
    let mut max_calories = 0;
    let mut current_calories = 0;
    let mut calories_per_elf: Vec<i32> = Vec::new();
    for line in lines {
        if line.eq("") {
            if current_calories > max_calories {
                max_calories = current_calories;
            }
            calories_per_elf.push(current_calories);
            current_calories = 0;
        } else {
            let calories: i32 = match line.trim().parse() {
                Ok(num) => num,
                Err(_) => continue,
            };
            current_calories += calories;
        }
    }
    println!("Max calories: {max_calories}.");
    calories_per_elf.sort();
    let number_of_elves = calories_per_elf.len();
    let sum_top3_calories = calories_per_elf[number_of_elves - 1]
        + calories_per_elf[number_of_elves - 2]
        + calories_per_elf[number_of_elves - 3];
    println!("The top 3 elves are carrying {sum_top3_calories} calories");
}
