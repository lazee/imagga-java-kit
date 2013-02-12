package net.jakobnielsen.imagga.crop_slice.bean;

import java.util.List;

public class SmartCropping {

    private final String url;

    private final List<Cropping> croppings;

    public SmartCropping(String url, List<Cropping> croppings) {
        this.url = url;
        this.croppings = croppings;
    }

    public String getUrl() {
        return url;
    }

    public List<Cropping> getCroppings() {
        return croppings;
    }
}
