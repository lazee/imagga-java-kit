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

package net.jakobnielsen.imagga.color.client;

import net.jakobnielsen.imagga.color.bean.Color;
import net.jakobnielsen.imagga.color.bean.ColorIndexType;

import java.util.ArrayList;
import java.util.List;

public class RankSimilarColorRequest {

    private List<Color> colorVector;

    private ColorIndexType colorIndexType;

    private Integer dist;

    private Integer count;

    public RankSimilarColorRequest() {
    }

    public void setColorVector(List<Color> colorVector) {
        this.colorVector = colorVector != null ? colorVector : new ArrayList<Color>();
    }

    public void setColorIndexType(ColorIndexType colorIndexType) {
        this.colorIndexType = colorIndexType != null ? colorIndexType : ColorIndexType.OVERALL;
    }

    public void setDist(Integer dist) {
        this.dist = dist != null ? dist : 3000;
    }

    public void setCount(Integer count) {
        this.count = count != null ? count : 1000;
    }

    public List<Color> getColorVector() {
        return colorVector;
    }

    public ColorIndexType getColorIndexType() {
        return colorIndexType;
    }

    public Integer getDist() {
        return dist;
    }

    public Integer getCount() {
        return count;
    }
}
