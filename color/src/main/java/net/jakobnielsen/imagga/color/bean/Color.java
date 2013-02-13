package net.jakobnielsen.imagga.color.bean;

public class Color {

    private final Double percent;

    private final Long r;

    private final Long g;

    private final Long b;

    private final String htmlCode;

    private final String closestPaletteColor;

    private final String closestPaletteColorParent;

    private final Double closestPaletteDistance;

    public Color(Double percent, Long r, Long g, Long b, String htmlCode, String closestPaletteColor,
            String closestPaletteColorParent, Double closestPaletteDistance) {
        this.percent = percent;
        this.r = r;
        this.g = g;
        this.b = b;
        this.htmlCode = htmlCode;
        this.closestPaletteColor = closestPaletteColor;
        this.closestPaletteColorParent = closestPaletteColorParent;
        this.closestPaletteDistance = closestPaletteDistance;
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
