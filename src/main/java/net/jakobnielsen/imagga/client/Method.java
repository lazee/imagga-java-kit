package net.jakobnielsen.imagga.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Method {

    private final Map<String, List<String>> params;

    public Method(String name) {
        this.params = new HashMap<String, List<String>>();
        this.addParam("method", name);
    }

    public void addParam(String name, List<String> values) {
        params.put(name, values);
    }

    public void addParam(String name, String... values) {
        params.put(name, Arrays.asList(values));
    }

    public boolean hasParam(String name) {
        return this.params.containsKey(name);

    }

    public List<String> getParam(String name) {
        return params.get(name);
    }

    public Map<String, List<String>> getParams() {
        return params;
    }
}
