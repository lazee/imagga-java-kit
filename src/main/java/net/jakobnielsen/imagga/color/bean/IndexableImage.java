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

package net.jakobnielsen.imagga.color.bean;

public class IndexableImage {

    private final Integer id;

    private final String url;

    public IndexableImage(String url, int id) {
        this.url = url;
        this.id = id;
    }

    public IndexableImage(String url, Integer id) {
        this.url = url;
        this.id = id;
    }

    public IndexableImage(String url) {
        this.url = url;
        this.id = null;
    }

    public Integer getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
