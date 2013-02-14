package net.jakobnielsen.imagga.color.client;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class ColorAPIClientIntegrationTest {


    private ColorAPIClient client;

    @Before
    public void setUp() {
        client =
                new ColorAPIClient(
                        System.getenv("IMAGGA_KEY"),
                        System.getenv("IMAGGA_SECRET"),
                        System.getenv("IMAGGA_ENDPOINT"));
    }

    @Test
    public void testColorsByUrls() {
        client.colorsByUrls(
                Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png",
                        "http://www.toondoo.com/public/l/a/z/lazee/toons/cool-cartoon-152229.png"));
    }

}
