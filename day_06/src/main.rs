use std::collections::HashSet;
use std::fs;
fn main() {
    let contents =
        fs::read_to_string("./input.txt").expect("Should have been able to read the file");
    let lines: Vec<&str> = contents.split('\n').filter(|line| line.len() > 0).collect();
    let &line = lines.get(0).unwrap();
    let mut index = 0;
    while index < &line.len() - 15 {
        let marker = &line[index..index + 14];
        println!("{marker}");
        let chars: HashSet<char> = marker.chars().collect();
        if chars.len() == 14 {
            break;
        }
        index += 1;
    }
    println!("{}", index + 14) ;
}
