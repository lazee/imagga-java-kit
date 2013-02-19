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

public class ExtendedColor extends Color {

    private final String htmlCode;

    private final String closestPaletteColor;

    private final String closestPaletteColorParent;

    private final Double closestPaletteDistance;


    public ExtendedColor(Double percent, Long r, Long g, Long b, String htmlCode, String closestPaletteColor,
            String closestPaletteColorParent, Double closestPaletteDistance) {
        super(percent, r, g, b);
        this.htmlCode = htmlCode;
        this.closestPaletteColor = closestPaletteColor;
        this.closestPaletteColorParent = closestPaletteColorParent;
        this.closestPaletteDistance = closestPaletteDistance;
    }

    public String getHtmlCode() {
        return htmlCode;
    }

    public String getClosestPaletteColor() {
        return closestPaletteColor;
    }

    public String getClosestPaletteColorParent() {
        return closestPaletteColorParent;
    }

    public Double getClosestPaletteDistance() {
        return closestPaletteDistance;
    }

}
