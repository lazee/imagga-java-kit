package net.jakobnielsen.imagga.crop_slice.convert;

import junit.framework.Assert;
import net.jakobnielsen.imagga.ConverterException;
import net.jakobnielsen.imagga.crop_slice.bean.Cropping;
import net.jakobnielsen.imagga.crop_slice.bean.Region;
import net.jakobnielsen.imagga.crop_slice.bean.SmartCropping;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SmartCroppingConverterTest extends AbstractConverterTester {

    private String JSON;

    @Before
    public void setUp() throws Exception {
        JSON = readTestResource("smartcroppings");
    }

    @Test(expected = ConverterException.class)
    public void testConvertNull() {
        SmartCroppingConverter converter = new SmartCroppingConverter();
        Assert.assertNull(converter.convert(null));
    }

    @Test(expected = ConverterException.class)
    public void testConvertEmpty() {
        SmartCroppingConverter converter = new SmartCroppingConverter();
        Assert.assertNull(converter.convert("{}"));
    }

    @Test
    public void testConvert() throws Exception {
        SmartCroppingConverter converter = new SmartCroppingConverter();
        List<SmartCropping> smartCroppings = converter.convert(JSON);

        Assert.assertNotNull(smartCroppings);
        Assert.assertEquals(2, smartCroppings.size());

        SmartCropping smartCropping1 = smartCroppings.get(0);
        Assert.assertNotNull(smartCropping1);
        Assert.assertEquals("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png", smartCropping1.getUrl());
        Assert.assertNotNull(smartCropping1.getCroppings());
        Assert.assertEquals(2, smartCropping1.getCroppings().size());
        Cropping cropping1 = smartCropping1.getCroppings().get(0);
        Assert.assertNotNull(cropping1);
        Assert.assertNotNull(cropping1.getTargetWidth());
        Assert.assertEquals(50, cropping1.getTargetWidth().intValue());
        Assert.assertNotNull(cropping1.getTargetHeight());
        Assert.assertEquals(49, cropping1.getTargetHeight().intValue());
        Region region1 = cropping1.getRegion();
        Assert.assertNotNull(region1);
        Assert.assertNotNull(region1.getX1());
        Assert.assertEquals(364, region1.getX1().intValue());
        Assert.assertNotNull(region1.getY1());
        Assert.assertEquals(179, region1.getY1().intValue());
        Assert.assertNotNull(region1.getX2());
        Assert.assertEquals(414, region1.getX2().intValue());
        Assert.assertNotNull(region1.getY2());
        Assert.assertEquals(229, region1.getY2().intValue());
    }
}
