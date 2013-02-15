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
