package net.jakobnielsen.imagga.color.bean;

import java.util.List;

public class Info {

    private final List<Color> imageColors;

    private final List<Color> foregroundColors;

    private final List<Color> backgroundColors;

    private final Double objectPercentage;

    private final Long colorVariance;

    //"image_packed":null,
    //"foreground_packed":null,
    //"background_packed":null


    public Info(List<Color> imageColors, List<Color> foregroundColors,
            List<Color> backgroundColors, Double objectPercentage, Long colorVariance) {
        this.imageColors = imageColors;
        this.foregroundColors = foregroundColors;
        this.backgroundColors = backgroundColors;
        this.objectPercentage = objectPercentage;
        this.colorVariance = colorVariance;
    }

    public List<Color> getImageColors() {
        return imageColors;
    }

    public List<Color> getForegroundColors() {
        return foregroundColors;
    }

    public List<Color> getBackgroundColors() {
        return backgroundColors;
    }

    public Double getObjectPercentage() {
        return objectPercentage;
    }

    public Long getColorVariance() {
        return colorVariance;
    }
}
