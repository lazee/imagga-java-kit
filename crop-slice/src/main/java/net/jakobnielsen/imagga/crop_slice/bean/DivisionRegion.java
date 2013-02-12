package net.jakobnielsen.imagga.crop_slice.bean;

import java.util.List;

public class DivisionRegion {

    private final String url;

    private final List<Region> regions;

    public DivisionRegion(String url, List<Region> regions) {
        this.url = url;
        this.regions = regions;
    }

    public String getUrl() {
        return url;
    }

    public List<Region> getRegions() {
        return regions;
    }
}
