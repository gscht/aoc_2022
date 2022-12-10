use std::fs;

fn main() {
    let contents =
        fs::read_to_string("./input.txt").expect("Should have been able to read the file");
    let lines: Vec<&str> = contents.split('\n').filter(|line| line.len() > 0).collect();

    let mut ticks = 1;
    let mut x = 1;
    let mut signal = 0;
    for line in lines {
        if line == "noop" {
            if ticks == 20
                || ticks == 60
                || ticks == 100
                || ticks == 140
                || ticks == 180
                || ticks == 220
            {
                signal += x * ticks;
            }
            print(x, ticks);
            ticks += 1;
        } else if line.starts_with("addx ") {
            if ticks == 20
                || ticks == 60
                || ticks == 100
                || ticks == 140
                || ticks == 180
                || ticks == 220
            {
                signal += x * ticks;
            }
            print(x, ticks);
            ticks += 1;
            if ticks == 20
                || ticks == 60
                || ticks == 100
                || ticks == 140
                || ticks == 180
                || ticks == 220
            {
                signal += x * ticks;
            }
            print(x, ticks);
            ticks += 1;
            let num: i32 = match line[5..].parse() {
                Ok(n) => n,
                Err(_) => panic!("Cannot read"),
            };
            x += num;
        } else {
            panic!("Unkown line");
        }
    }

    println!("");
    println!("Signal: {}", signal);
}

fn print(x: i32, ticks: i32) {
    let row = (ticks-1) / 40;
    let tick_per_row = ticks - row * 40;
    if tick_per_row >= x && tick_per_row <= x+2 {
        print!("#");
    } else {
        print!(".")
    }
    if ticks % 40 == 0 {
        print!("\n");
    }
}
