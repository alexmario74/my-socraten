package org.mas.netstats;

import java.util.HashMap;
import java.util.Map;

public class NetReport implements Report {
    private int countRadio = 0;
    private int countCore = 0;

    @Override
    public void incrementCounter(String counterName) {
        if ("countRadio".equals(counterName)) {
            countRadio++;
        } else if ("countCore".equals(counterName)) {
            countCore++;
        } else {
            throw new IllegalArgumentException("counter " + counterName + " is not defined");
        }
    }

    @Override
    public Map<String, Integer> listCounters() {
        Map<String, Integer> counters = new HashMap<>();

        counters.put("countRadio", countRadio);
        counters.put("countCore", countCore);

        return counters;
    }
}
