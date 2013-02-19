/*
 * Copyright 2013 Jakob Vad Nielsen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
