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
