package net.jakobnielsen.imagga.crop_slice.convert;

import junit.framework.Assert;
import net.jakobnielsen.imagga.client.AbstractConverterTester;
import net.jakobnielsen.imagga.convert.ConverterException;
import net.jakobnielsen.imagga.crop_slice.bean.ApiUsage;
import net.jakobnielsen.imagga.crop_slice.bean.Usage;
import org.junit.Before;
import org.junit.Test;

public class ApiUsageConverterTest extends AbstractConverterTester {

    private String JSON;

    @Before
    public void setUp() throws Exception {
        JSON = readTestResource("apiusage");
    }

    @Test(expected = ConverterException.class)
    public void testConvertNull() {
        ApiUsageConverter converter = new ApiUsageConverter();
        Assert.assertNull(converter.convert(null));
    }

    @Test(expected = ConverterException.class)
    public void testConvertEmpty() {
        ApiUsageConverter converter = new ApiUsageConverter();
        Assert.assertNull(converter.convert("{}"));
    }

    @Test
    public void testConvert() {
        ApiUsageConverter converter = new ApiUsageConverter();
        ApiUsage apiUsage = converter.convert(JSON);
        Assert.assertNotNull(apiUsage);
        Assert.assertNotNull(apiUsage.getStartTime());
        Assert.assertEquals(1359669600, apiUsage.getStartTime().getTime());
        Assert.assertEquals(1360649311, apiUsage.getEndTime().getTime());
        Assert.assertEquals(2.7592, apiUsage.getTotalPayable());
        Assert.assertEquals(4, apiUsage.getUsageMap().size());
        Usage u = apiUsage.getUsageMap().get("F1_usage");
        Assert.assertNotNull(u);
        Assert.assertEquals(133, u.getCount().longValue());
        Assert.assertEquals(1.995, u.getTotalPrice());
        Usage u2 = apiUsage.getUsageMap().get("F2_additional_usage");
        Assert.assertNotNull(u2);
        Assert.assertEquals(74, u2.getCount().longValue());
        Assert.assertEquals(0.2442, u2.getTotalPrice());
    }
}
