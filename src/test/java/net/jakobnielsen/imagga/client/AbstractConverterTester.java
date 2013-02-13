package net.jakobnielsen.imagga.client;

import java.io.InputStream;

public abstract class AbstractConverterTester {

    public String readTestResource(String resource) {
        InputStream is = this.getClass().getResourceAsStream("/json/" + resource + ".json");
        if (is == null) {
            return null;
        }
        java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
