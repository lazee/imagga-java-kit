package net.jakobnielsen.imagga.color.bean;

public class ColorResult {

    private final String url;

    private final Info info;

    public ColorResult(String url, Info info) {
        this.url = url;
        this.info = info;
    }

    public String getUrl() {
        return url;
    }

    public Info getInfo() {
        return info;
    }
}
