use std::fs;
fn main() {
    let contents =
        fs::read_to_string("./input.txt").expect("Should have been able to read the file");
    let lines: Vec<&str> = contents.split('\n').filter(|line| line.len() > 0).collect();

    let mut stack_1: Vec<char> = Vec::new();
    let mut stack_2: Vec<char> = Vec::new();
    let mut stack_3: Vec<char> = Vec::new();
    let mut stack_4: Vec<char> = Vec::new();
    let mut stack_5: Vec<char> = Vec::new();
    let mut stack_6: Vec<char> = Vec::new();
    let mut stack_7: Vec<char> = Vec::new();
    let mut stack_8: Vec<char> = Vec::new();
    let mut stack_9: Vec<char> = Vec::new();

    let all_stacks = [
        &mut stack_1,
        &mut stack_2,
        &mut stack_3,
        &mut stack_4,
        &mut stack_5,
        &mut stack_6,
        &mut stack_7,
        &mut stack_8,
        &mut stack_9,
    ];

    for line in lines {
        if line.starts_with(&"move") || line.starts_with(&" 1") {
            break;
        }
        let mut stack_counter = 1;
        while stack_counter < 10 {
            let stack = &line[(stack_counter - 1) * 4..(stack_counter * 4) - 1].trim();
            if stack.len() > 0 {
                let mut chars = stack.chars();
                chars.next();
                let crate_ = chars.next().unwrap();
                let _ = &all_stacks[stack_counter - 1].push(crate_);
            }
            stack_counter += 1;
        }
    }

    let mut stack_counter = 0;
    while stack_counter < 9 {
        let _ = &all_stacks[stack_counter].reverse();
        stack_counter += 1;
    }
    

    let lines: Vec<&str> = contents
        .split('\n')
        .filter(|line| line.starts_with("move"))
        .collect();
    // move 2 from 2 to 8
    for line in lines {
        println!("{line}");
        let from_start = line.find("from").unwrap();
        let number_of_items = &line[5..from_start - 1];
        let mut number_of_items: i32 = match number_of_items.parse() {
            Ok(num) => num,
            Err(_) => continue,
        };
        println!(">{number_of_items}<");

        let from_stack = &line[line.len() - 6..line.len() - 5];
        let from_stack: usize = match from_stack.parse::<usize>() {
            Ok(num) => num - 1,
            Err(_) => continue,
        };
        println!(">{from_stack}<");

        let target_stack = &line[line.len() - 1..];

        let target_stack: usize = match target_stack.parse::<usize>() {
            Ok(num) => num - 1,
            Err(_) => continue,
        };
        println!(">{target_stack}<");

        let mut helper_stac: Vec<char> = Vec::new();
        let mut number_of_itmes_2 = number_of_items;
        while number_of_items > 0 {
            let c = all_stacks[from_stack].pop().unwrap();
            helper_stac.push(c);
            //all_stacks[target_stack].push(c);

            number_of_items -= 1;
        }

        while number_of_itmes_2 > 0 {
            let c = helper_stac.pop().unwrap();
            all_stacks[target_stack].push(c);
            number_of_itmes_2 -= 1;
        }
    }

    let mut stack_counter = 0;
    while stack_counter < 9 {
        print!("{}", &all_stacks[stack_counter].get(&all_stacks[stack_counter].len() - 1).unwrap());
        stack_counter += 1;
    }
    println!("");
}
