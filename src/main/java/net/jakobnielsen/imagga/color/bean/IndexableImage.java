package net.jakobnielsen.imagga.color.bean;

public class IndexableImage {

    private final Long id;

    private final String url;

    public IndexableImage(String url, long id) {
        this.url = url;
        this.id = id;
    }

    public IndexableImage(String url, Long id) {
        this.url = url;
        this.id = id;
    }

    public IndexableImage(String url) {
        this.url = url;
        this.id = null;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
