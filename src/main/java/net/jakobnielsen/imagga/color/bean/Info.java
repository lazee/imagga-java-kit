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

import java.util.List;

public class Info {

    private final List<ExtendedColor> imageColors;

    private final List<ExtendedColor> foregroundColors;

    private final List<ExtendedColor> backgroundColors;

    private final Double objectPercentage;

    private final Long colorVariance;

    //"image_packed":null,
    //"foreground_packed":null,
    //"background_packed":null


    public Info(List<ExtendedColor> imageColors, List<ExtendedColor> foregroundColors,
            List<ExtendedColor> backgroundColors, Double objectPercentage, Long colorVariance) {
        this.imageColors = imageColors;
        this.foregroundColors = foregroundColors;
        this.backgroundColors = backgroundColors;
        this.objectPercentage = objectPercentage;
        this.colorVariance = colorVariance;
    }

    public List<ExtendedColor> getImageColors() {
        return imageColors;
    }

    public List<ExtendedColor> getForegroundColors() {
        return foregroundColors;
    }

    public List<ExtendedColor> getBackgroundColors() {
        return backgroundColors;
    }

    public Double getObjectPercentage() {
        return objectPercentage;
    }

    public Long getColorVariance() {
        return colorVariance;
    }
}
