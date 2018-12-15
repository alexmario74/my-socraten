package org.mas.netstats;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ReportPrinter implements Printer {
    private Report report;

    public ReportPrinter(Report report) {
        this.report = report;
    }

    @Override
    public void print(OutputStream os) throws IOException {
        Map<String, Integer> counters = report.listCounters();

        os.write("(".getBytes());

        int len = counters.size();
        for(Map.Entry<String, Integer> entry : counters.entrySet()) {
            len--;
            os.write(entry.getValue().toString().getBytes());
            if (len > 0) os.write(", ".getBytes());
        }
        os.write(")\n".getBytes());
    }
}
