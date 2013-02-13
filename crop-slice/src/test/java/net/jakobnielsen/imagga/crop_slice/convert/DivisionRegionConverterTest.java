package net.jakobnielsen.imagga.crop_slice.convert;

import junit.framework.Assert;
import net.jakobnielsen.imagga.convert.ConverterException;
import net.jakobnielsen.imagga.crop_slice.bean.DivisionRegion;
import net.jakobnielsen.imagga.crop_slice.bean.Region;
import net.jakobnielsen.imagga.test.AbstractConverterTester;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DivisionRegionConverterTest extends AbstractConverterTester {

    private String JSON;

    @Before
    public void setUp() throws Exception {
        JSON = readTestResource("divisionregions");
    }

    @Test(expected = ConverterException.class)
    public void testConvertNull() {
        DivisionRegionConverter converter = new DivisionRegionConverter();
        Assert.assertNull(converter.convert(null));
    }

    @Test(expected = ConverterException.class)
    public void testConvertEmpty() {
        DivisionRegionConverter converter = new DivisionRegionConverter();
        Assert.assertNull(converter.convert("{}"));
    }

    @Test
    public void testConvert() throws Exception {
        DivisionRegionConverter converter = new DivisionRegionConverter();
        List<DivisionRegion> divisionRegions = converter.convert(JSON);

        Assert.assertNotNull(divisionRegions);
        Assert.assertEquals(2, divisionRegions.size());

        DivisionRegion divisionRegion1 = divisionRegions.get(0);
        Assert.assertNotNull(divisionRegion1);
        Assert.assertEquals("http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png", divisionRegion1.getUrl());

        Assert.assertNotNull(divisionRegion1.getRegions());
        Assert.assertEquals(3, divisionRegion1.getRegions().size());

        Region region1 = divisionRegion1.getRegions().get(0);
        Assert.assertNotNull(region1);
        Assert.assertNotNull(region1.getX1());
        Assert.assertEquals(268, region1.getX1().intValue());
        Assert.assertNotNull(region1.getY1());
        Assert.assertEquals(13, region1.getY1().intValue());
        Assert.assertNotNull(region1.getX2());
        Assert.assertEquals(500, region1.getX2().intValue());
        Assert.assertNotNull(region1.getY2());
        Assert.assertEquals(299, region1.getY2().intValue());
    }
}
