package net.jakobnielsen.imagga.crop_slice.client;

import net.jakobnielsen.imagga.crop_slice.bean.ApiUsage;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class CropSliceAPIClientIntegrationTest {

    private CropSliceAPIClient client;

    @Before
    public void setUp() {
        client = new CropSliceAPIClient(
                System.getenv("IMAGGA_KEY"),
                System.getenv("IMAGGA_SECRET"),
                System.getenv("IMAGGA_ENDPOINT"));
    }

    @Test
    public void testDivisionRegionsByUrls() {
        client.divisionRegionsByUrls(
                Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png"));

    }

    @Test
    public void testSmartCroppingByUrls() {
        client.smartCroppingByUrls(
                Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png"),
                Arrays.asList("50", "50"), true);
    }

    @Test
    public void testGetApiUsage() {
        ApiUsage apiUsage = client.apiUsage(0, 0);
        System.out.println(apiUsage.toString());
    }

}
