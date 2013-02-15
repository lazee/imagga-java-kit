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
