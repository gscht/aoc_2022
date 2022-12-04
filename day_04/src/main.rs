use std::fs;
fn main() {
    let contents =
        fs::read_to_string("./input.txt").expect("Should have been able to read the file");

    let lines: Vec<&str> = contents.split('\n').collect();
    let mut complete_overlaps = 0;
    let mut partly_overlaps = 0;
    for line in lines {
        if line == "" {
            continue;
        }
        let pairs: Vec<&str> = line.split(",").collect();

        let left = pairs.get(0).unwrap();
        let right = pairs.get(1).unwrap();

        let left_boundaries: Vec<&str> = left.split("-").collect();
        let right_boundaries: Vec<&str> = right.split("-").collect();

        let left_low: i32 = match left_boundaries.get(0).unwrap().parse() {
            Ok(num) => num,
            Err(_) => continue,
        };

        let left_high: i32 = match left_boundaries.get(1).unwrap().parse() {
            Ok(num) => num,
            Err(_) => continue,
        };

        let right_low: i32 = match right_boundaries.get(0).unwrap().parse() {
            Ok(num) => num,
            Err(_) => continue,
        };

        let right_high: i32 = match right_boundaries.get(1).unwrap().parse() {
            Ok(num) => num,
            Err(_) => continue,
        };

        if left_low >= right_low && left_high <= right_high {
            complete_overlaps += 1;
        } else if right_low >= left_low && right_high <= left_high {
            complete_overlaps += 1;
        }

        if left_high >= right_low && left_high <= right_high {
            partly_overlaps += 1;
        } else if right_high >= left_low && right_high <= left_high {
            partly_overlaps += 1;
        }
    }
    println!("{complete_overlaps}");
    println!("{partly_overlaps}");
}
