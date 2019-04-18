extern crate netstats;

use std::env::args_os;

fn main() {
    if let Some(file_name) = args_os().nth(1) {
        println!("going to read file: {:?}", file_name);
        match netstats::analyze_file(file_name.to_str().unwrap()) {
            Ok(report) => println!("Report:\n{:?}", report),
            Err(err) => eprintln!("Error {:?}", err),
        }
    } else {
        eprintln!("a valid csv file name should be provided.");
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn analyze_file() {
        match netstats::analyze_file("topology.csv") {
            Ok(report) => assert_eq!(report, (274703, 371)),
            Err(err) => assert!(false, "{:?}", err),
        }
    }
}
