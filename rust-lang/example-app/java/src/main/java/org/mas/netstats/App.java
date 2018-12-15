package org.mas.netstats;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("error: a valid csv file path should be provided.");
            System.exit(-1);
        }

        Netstats app = new Netstats();

        String csvFileName = args[0];
        System.out.println("going to parse " + csvFileName);

        Report r = app.analyzeFile(csvFileName);

        new ReportPrinter(r).print(System.out);
    }
}
