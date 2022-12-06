use std::collections::HashSet;
use std::fs;
fn main() {
    let contents =
        fs::read_to_string("./input.txt").expect("Should have been able to read the file");
    let lines: Vec<&str> = contents.split('\n').filter(|line| line.len() > 0).collect();
    let &line = lines.get(0).unwrap();
    let mut index = 0;
    while index < &line.len() - 5 {
        let marker = &line[index..index + 4];
        println!("{marker}");
        let chars: HashSet<char> = marker.chars().collect();
        if chars.len() == 4 {
            break;
        }
        index += 1;
    }
    println!("{}", index + 4) ;
}
