package org.mas.netstats;

import java.io.IOException;
import java.io.OutputStream;

public interface Printer {

    void print(OutputStream os) throws IOException;
}
