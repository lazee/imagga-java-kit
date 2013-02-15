package net.jakobnielsen.imagga.color.bean;

public enum ColorIndexType {

    OVERALL("overall"), OBJECT("object"), BACKGROUND("background");

    private String name;

    private ColorIndexType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
