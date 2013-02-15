package net.jakobnielsen.imagga.color.bean;

public class Color {

    private final Double percent;

    private final Long r;

    private final Long g;

    private final Long b;

    public Color(double percent, long r, long g, long b) {
        this.percent = percent;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(Double percent, Long r, Long g, Long b) {
        this.percent = percent;
        this.r = r;
        this.g = g;
        this.b = b;
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

}
