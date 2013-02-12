package net.jakobnielsen.imagga.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;


public class APIClientTest {

    private APIClient apiClient;

    @Before
    public void setUp() throws Exception {
        apiClient = new APIClient("acc_c6fbe2", "abcdef123456", "8.8.8.1");
    }

    @Test
    public void testImplode() throws Exception {
        Assert.assertEquals("1,2,3,4", apiClient.implode(Arrays.asList("1","2","3","4"),","));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImplodeNull() throws Exception {
        Assert.assertNull(apiClient.implode(null, null));
    }

    @Test
    public void testGenerateSig() throws Exception {
        Method m = new Method("imagga.process.crop");
        m.addParam("api_key", "acc_c652fbf4");
        m.addParam("no_scaling", "0");
        m.addParam("resolutions", "150,200,100,50,75,75");
        m.addParam("urls",
                "http://www.stockpodium.com/stock-photo-7890736/couple-child-spending-time-together-image.jpg" +
                        ",http://www.stockpodium.com/stock-photo-7314730/hand-keys-car-image.jpg");
        m.addParam("v", "1.0");

        Assert.assertEquals("1d8ecb284c2538a88ae4a4a90dd387e2", apiClient.generateSig(m.getParams()));
    }
}
