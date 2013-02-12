package net.jakobnielsen.imagga.crop_slice.client;

import net.jakobnielsen.imagga.crop_slice.bean.ApiUsage;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class CropSliceAPIClientTest {

    private CropSliceAPIClient cropSliceAPIClient;

    @Before
    public void setUp() {
        cropSliceAPIClient =
                new CropSliceAPIClient("", "", "");
    }

    @Test
    public void testDivisionRegionsByUrls() {
        cropSliceAPIClient.divisionRegionsByUrls(
                Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png"));

    }

    @Test
    public void testSmartCroppingByUrls() {
        cropSliceAPIClient.smartCroppingByUrls(
                Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png"),
                Arrays.asList("50", "50"), true);
    }

    @Test
    public void testGetApiUsage() {
        ApiUsage apiUsage = cropSliceAPIClient.apiUsage(0, 0);
        System.out.println(apiUsage.toString());
    }

}
