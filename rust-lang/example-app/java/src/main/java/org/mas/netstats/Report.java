package org.mas.netstats;

import java.util.Map;

public interface Report {
    void incrementCounter(String counterName);
    Map<String, Integer> listCounters();
}
