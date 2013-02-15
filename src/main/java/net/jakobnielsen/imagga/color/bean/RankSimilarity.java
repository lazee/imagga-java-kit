package net.jakobnielsen.imagga.color.bean;

public class RankSimilarity {

    private final Long id;

    private final Double dist;

    public RankSimilarity(Long id, Double dist) {
        this.id = id;
        this.dist = dist;
    }

    public Long getId() {
        return id;
    }

    public Double getDist() {
        return dist;
    }
}
