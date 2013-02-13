package net.jakobnielsen.imagga.crop_slice.bean;

public class Region {

    private final Long x1;

    private final Long y1;

    private final Long x2;

    private final Long y2;

    public Region(Long x1, Long y1, Long x2, Long y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Long getX1() {
        return x1;
    }

    public Long getY1() {
        return y1;
    }

    public Long getX2() {
        return x2;
    }

    public Long getY2() {
        return y2;
    }
}
