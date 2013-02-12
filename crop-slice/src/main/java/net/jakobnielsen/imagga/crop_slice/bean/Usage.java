package net.jakobnielsen.imagga.crop_slice.bean;

public class Usage {

    private final Long count;

    private final Double totalPrice;

    public Usage(Long count, Double totalPrice) {
        this.count = count;
        this.totalPrice = totalPrice;
    }

    public Long getCount() {
        return count;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }
}
