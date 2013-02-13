package net.jakobnielsen.imagga.convert;

public interface Converter <E> {

    E convert(String jsonString);

}
