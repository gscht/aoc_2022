use std::fs;

#[derive(PartialEq)]
enum Symbol {
    Rock,
    Paper,
    Scissors,
}

enum Outcome {
    Loose,
    Draw,
    Win,
}

fn main() {
    let c1 = 'A';
    let c2 = 'A';
    if c1 == c2 {
        println!("OK");
    }
    let contents =
        fs::read_to_string("./input.txt").expect("Should have been able to read the file");

    let lines: Vec<&str> = contents.split('\n').collect();
    // A, X -> Rock
    // B, Y -> Paper
    // C, Z, -> Scissors
    let mut points = 0;
    for line in lines {
        if line.len() == 0 {
            continue;
        }
        let p1 = line.chars().nth(0).unwrap();
        let p1: Symbol = match p1 {
            'A' => Symbol::Rock,
            'B' => Symbol::Paper,
            'C' => Symbol::Scissors,
            _other => panic!("Unknown input {p1}"),
        };
        let p2 = line.chars().nth(2).unwrap();
        // X need to loose, Y draw, Z win
        let p2: Outcome = match p2 {
            'X' => Outcome::Loose,
            'Y' => Outcome::Draw,
            'Z' => Outcome::Win,
            _other => panic!("Unknown input {p2}"),
        };

        let p2: Symbol = match p2 {
            Outcome::Win => get_winning_symbol(p1),
            Outcome::Loose => get_loosing_symbol(p1),
            Outcome::Draw => get_draw_symbol(p1),
        };

        // Add score for shape
        points += match p2 {
            Symbol::Rock => 1,
            Symbol::Paper => 2,
            Symbol::Scissors => 3,
        };

        // Add score for result
        if p1 == p2 {
            points += 3;
        } else if p2 == Symbol::Rock && p1 == Symbol::Scissors {
            //println!("Won {p1} {p2}");
            points += 6;
        } else if p2 == Symbol::Paper && p1 == Symbol::Rock {
            //println!("Won {p1} {p2}");
            points += 6;
        } else if p2 == Symbol::Scissors && p1 == Symbol::Paper {
            //println!("Won {p1} {p2}");
            points += 6;
        } else {
            //println!("Lost {p1} {p2}");
        }
    }
    println!("Your points: {points}");
}

fn get_draw_symbol(s: Symbol) -> Symbol {
    match s {
        Symbol::Rock => Symbol::Rock,
        Symbol::Paper => Symbol::Paper,
        Symbol::Scissors => Symbol::Scissors,
    }
}

fn get_winning_symbol(s: Symbol) -> Symbol {
    match s {
        Symbol::Rock => Symbol::Paper,
        Symbol::Paper => Symbol::Scissors,
        Symbol::Scissors => Symbol::Rock,
    }
}
fn get_loosing_symbol(s: Symbol) -> Symbol {
    match s {
        Symbol::Rock => Symbol::Scissors,
        Symbol::Paper => Symbol::Rock,
        Symbol::Scissors => Symbol::Paper,
    }
}
