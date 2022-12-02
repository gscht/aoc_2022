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
    let mut points = 0;
    for line in lines {
        if line.len() == 0 {
            continue;
        }

        let player1_symbol = line.chars().nth(0).unwrap();
        let player1_symbol: Symbol = match player1_symbol {
            'A' => Symbol::Rock,
            'B' => Symbol::Paper,
            'C' => Symbol::Scissors,
            _other => panic!("Unknown input {player1_symbol}"),
        };

        let player2_symbol = line.chars().nth(2).unwrap();
        let outcome: Outcome = match player2_symbol {
            'X' => Outcome::Loose,
            'Y' => Outcome::Draw,
            'Z' => Outcome::Win,
            _other => panic!("Unknown input {player2_symbol}"),
        };

        // Get symbol so that outcome is fulfilled.
        let player2_symbol: Symbol = match outcome {
            Outcome::Win => get_winning_symbol(&player1_symbol),
            Outcome::Loose => get_loosing_symbol(&player1_symbol),
            Outcome::Draw => get_draw_symbol(&player1_symbol),
        };

        // Add score for shape
        points += match player2_symbol {
            Symbol::Rock => 1,
            Symbol::Paper => 2,
            Symbol::Scissors => 3,
        };

        // Add score for result
        if player1_symbol == player2_symbol {
            points += 3;
        } else if player2_symbol == Symbol::Rock && player1_symbol == Symbol::Scissors {
            points += 6;
        } else if player2_symbol == Symbol::Paper && player1_symbol == Symbol::Rock {
            points += 6;
        } else if player2_symbol == Symbol::Scissors && player1_symbol == Symbol::Paper {
            points += 6;
        } 
    }
    println!("Your points: {points}");
}

fn get_draw_symbol(s: &Symbol) -> Symbol {
    match s {
        Symbol::Rock => Symbol::Rock,
        Symbol::Paper => Symbol::Paper,
        Symbol::Scissors => Symbol::Scissors,
    }
}

fn get_winning_symbol(s: &Symbol) -> Symbol {
    match s {
        Symbol::Rock => Symbol::Paper,
        Symbol::Paper => Symbol::Scissors,
        Symbol::Scissors => Symbol::Rock,
    }
}
fn get_loosing_symbol(s: &Symbol) -> Symbol {
    match s {
        Symbol::Rock => Symbol::Scissors,
        Symbol::Paper => Symbol::Rock,
        Symbol::Scissors => Symbol::Paper,
    }
}
