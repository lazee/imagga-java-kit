package net.jakobnielsen.imagga.color.client;

import net.jakobnielsen.imagga.color.bean.IndexableImage;

import java.util.ArrayList;
import java.util.List;

public class ColorsByUrlsRequest {

    private List<IndexableImage> urlsToProcess;

    private boolean extractOverallColors;

    private boolean extractObjectColors;

    public ColorsByUrlsRequest() {
        urlsToProcess = new ArrayList<IndexableImage>();
        extractObjectColors = true;
        extractOverallColors = true;
    }

    public void setUrlsToProcess(List<IndexableImage> urlsToProcess) {
        this.urlsToProcess = urlsToProcess != null ? urlsToProcess : new ArrayList<IndexableImage>();
    }

    public void setExtractOverallColors(boolean extractOverallColors) {
        this.extractOverallColors = extractOverallColors;
    }

    public void setExtractObjectColors(boolean extractObjectColors) {
        this.extractObjectColors = extractObjectColors;
    }

    public List<IndexableImage> getUrlsToProcess() {
        return urlsToProcess;
    }

    public boolean isExtractOverallColors() {
        return extractOverallColors;
    }

    public boolean isExtractObjectColors() {
        return extractObjectColors;
    }
}
