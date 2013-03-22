package net.jakobnielsen.imagga.crop_slice.client;

import net.jakobnielsen.imagga.client.APIClientConfig;
import net.jakobnielsen.imagga.crop_slice.bean.ApiUsage;
import net.jakobnielsen.imagga.crop_slice.bean.DivisionRegion;
import net.jakobnielsen.imagga.crop_slice.bean.Resolution;
import net.jakobnielsen.imagga.crop_slice.bean.SmartCropping;
import net.jakobnielsen.imagga.upload.client.UploadClient;
import net.jakobnielsen.imagga.upload.client.UploadClientIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
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
    public void testSmartCroppingByUploadCode() throws IOException {
        UploadClient uploadClient = new UploadClient(APIClientConfig.load());
        File f = UploadClientIntegrationTest.createTestFile();
        List<SmartCropping> lst =
                client.smartCroppingByUploadCode(
                        uploadClient.uploadForProcessing(f),
                        true,
                        Arrays.asList(new Resolution(50, 50)),
                        true);
        Assert.assertNotNull(lst);
        Assert.assertEquals("50,50", lst.get(0).getCroppings().get(0).getTargetResolution().toString());
    }

    @Test
    public void testSmartCroppingByUrls() {
        List<SmartCropping> lst = client.smartCroppingByUrls(
                Arrays.asList("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png"),
                Arrays.asList(new Resolution(50, 50)), true);
        Assert.assertNotNull(lst);
    }

    @Test
    public void testGetApiUsage() {
        ApiUsage apiUsage = client.apiUsage(0, 0);
        Assert.assertNotNull(apiUsage);

    }

}
