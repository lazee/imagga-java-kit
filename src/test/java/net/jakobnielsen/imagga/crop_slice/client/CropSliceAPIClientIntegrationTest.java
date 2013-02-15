package net.jakobnielsen.imagga.crop_slice.client;

import net.jakobnielsen.imagga.client.APIClientConfig;
import net.jakobnielsen.imagga.crop_slice.bean.ApiUsage;
import net.jakobnielsen.imagga.crop_slice.bean.DivisionRegion;
import net.jakobnielsen.imagga.crop_slice.bean.SmartCropping;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CropSliceAPIClientIntegrationTest {

    private CropSliceAPIClient client;

    @Before
    public void setUp() throws IOException {
        client = new CropSliceAPIClient(APIClientConfig.load());
    }

    @Test
    public void testDivisionRegionsByUrls() {
        List<DivisionRegion> lst = client.divisionRegionsByUrls(
                Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png"));
        Assert.assertNotNull(lst);

    }

    @Test
    public void testSmartCroppingByUrls() {
        List<SmartCropping> lst = client.smartCroppingByUrls(
                Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png"),
                Arrays.asList("50", "50"), true);
        Assert.assertNotNull(lst);
    }

    @Test
    public void testGetApiUsage() {
        ApiUsage apiUsage = client.apiUsage(0, 0);
        Assert.assertNotNull(apiUsage);

    }

}
