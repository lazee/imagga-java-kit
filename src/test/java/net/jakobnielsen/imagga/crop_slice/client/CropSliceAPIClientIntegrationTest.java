package net.jakobnielsen.imagga.crop_slice.client;

import net.jakobnielsen.imagga.client.APIClientConfig;
import net.jakobnielsen.imagga.crop_slice.bean.ApiUsage;
import net.jakobnielsen.imagga.crop_slice.bean.DivisionRegion;
import net.jakobnielsen.imagga.crop_slice.bean.Region;
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

    @Test
    public void testSmartCroppingByUrls2() {
        List<SmartCropping> lst = client.smartCroppingByUrls(
                Arrays.asList("http://static.vg.no/uploaded/image/bilderigg/2013/03/25/1364198974009_765.jpg"),
                Arrays.asList(new Resolution(120, 80)), true);
        Assert.assertNotNull(lst);
        SmartCropping smt = lst.get(0);
        Assert.assertNotNull(smt);
        Assert.assertEquals(1, smt.getCroppings().size());
        Region r = smt.getCroppings().get(0).getRegion();
        Assert.assertEquals(455, r.getX1().intValue());
        Assert.assertEquals(575, r.getX2().intValue());
        Assert.assertEquals(221, r.getY1().intValue());
        Assert.assertEquals(301, r.getY2().intValue());

    }







}
