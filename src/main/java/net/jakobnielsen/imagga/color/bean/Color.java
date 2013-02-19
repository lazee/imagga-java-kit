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

public class Color {

    private final Double percent;

    private final Long r;

    private final Long g;

    private final Long b;

    public Color(double percent, long r, long g, long b) {
        this.percent = percent;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(Double percent, Long r, Long g, Long b) {
        this.percent = percent;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Double getPercent() {
        return percent;
    }

    public Long getR() {
        return r;
    }

    public Long getG() {
        return g;
    }

    public Long getB() {
        return b;
    }

}
