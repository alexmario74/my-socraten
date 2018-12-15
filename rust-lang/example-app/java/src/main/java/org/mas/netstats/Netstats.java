package org.mas.netstats;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Netstats {

    public Report analyzeFile(final String fileName) throws IOException {
        CSVParser parser = CSVParser.parse(Files.newBufferedReader(Paths.get(fileName)), CSVFormat.newFormat(';'));

        NetReport report = new NetReport();

        for (CSVRecord record : parser.getRecords()) {
            try {
                String moClass = calculateMoClass(record.get(0), record.get(3));

                MoKind service = MoKind.of(moClass);

                if (service.isRadio()) report.incrementCounter("countRadio");
                else if (service.isCore()) report.incrementCounter("countCore");
            } catch (IllegalArgumentException e) {
                // System.err.println(e.getMessage());
            }
        }

        return report;
    }

    private String calculateMoClass(String managementObject, String shortName) {
        String[] moByComponents = managementObject.split("/");
        String moSnWithType = moByComponents[moByComponents.length-1];

        if (!moSnWithType.contains("-" + shortName))
            throw new IllegalArgumentException("cannot determine MO class for " + managementObject + " with short nane " + shortName);

        return moSnWithType.substring(0, moSnWithType.indexOf("-" + shortName));
    }
}
