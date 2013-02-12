package net.jakobnielsen.imagga.crop_slice.convert;

public interface Converter <E> {

    E convert(String jsonString);

}
