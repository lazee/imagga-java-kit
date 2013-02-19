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

package net.jakobnielsen.imagga;

import java.util.List;

public class ListTools {

    public static final String LIST_SEPARATOR = ",";

    private ListTools() {
        // Intentional
    }

    public static String implode(List<?> list) {
        return implode(list, LIST_SEPARATOR);
    }

    public static String implode(List<?> list, String delimiter) {
        if (list == null) {
            throw new IllegalArgumentException("List argument is null");
        } else if (delimiter == null) {

        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString());
            if (i < list.size() - 1) {
                sb.append(delimiter == null ? LIST_SEPARATOR : delimiter);
            }
        }
        return sb.toString();
    }

}
