use std::fs;

fn main() {
    let contents =
        fs::read_to_string("./input.txt").expect("Should have been able to read the file");
    let lines: Vec<&str> = contents.split('\n').filter(|line| line.len() > 0).collect();

    let mut ticks = 0;
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
                println!("1, {} cycle, x={}", ticks, x);
                signal += x * ticks;
            }
            ticks += 1;
        } else if line.starts_with("addx ") {
            if ticks == 20
                || ticks == 60
                || ticks == 100
                || ticks == 140
                || ticks == 180
                || ticks == 220
            {
                println!("2, {} cycle, x={}", ticks, x);
                signal += x * ticks;
            }
            ticks += 1;
            if ticks == 20
                || ticks == 60
                || ticks == 100
                || ticks == 140
                || ticks == 180
                || ticks == 220
            {
                println!("3, {} cycle, x={}", ticks, x);
                signal += x * ticks;
            }
            ticks += 1;
            let num: i32 = match line[5..].parse() {
                Ok(n) => n,
                Err(_) => panic!("Cannot read"),
            };
            x += num;
        }
    }

    println!("Signal: {}", signal);
}
