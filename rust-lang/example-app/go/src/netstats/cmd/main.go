package main

import (
	"fmt"
	"netstats"
	"os"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("error: a valid csv file path should be provided.")
		os.Exit(-1)
	}

	fileName := os.Args[1]

	fmt.Printf("going to parse %s\n", fileName)

	countRadio, countCore, err := netstats.AnalyzeFile(fileName)
	if err != nil {
		fmt.Printf("got an error %v\n", err)
		os.Exit(-1)
	}

	fmt.Printf("(%d, %d)\n", countRadio, countCore)
}
