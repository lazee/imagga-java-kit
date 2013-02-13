package net.jakobnielsen.imagga.crop_slice.bean;

public class Cropping {

    private final Integer targetWidth;

    private final Integer targetHeight;

    private final Region region;

    public Cropping(Integer targetWidth, Integer targetHeight, Region region) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        this.region = region;
    }

    public Integer getTargetWidth() {
        return targetWidth;
    }

    public Integer getTargetHeight() {
        return targetHeight;
    }

    public Region getRegion() {
        return region;
    }
}
