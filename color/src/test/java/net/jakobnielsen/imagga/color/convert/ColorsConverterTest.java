package net.jakobnielsen.imagga.color.convert;

import junit.framework.Assert;
import net.jakobnielsen.imagga.color.bean.Color;
import net.jakobnielsen.imagga.color.bean.ColorResult;
import net.jakobnielsen.imagga.color.bean.Info;
import net.jakobnielsen.imagga.convert.ConverterException;
import net.jakobnielsen.imagga.test.AbstractConverterTester;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ColorsConverterTest extends AbstractConverterTester {

    private String JSON;

    @Before
    public void setUp() throws Exception {
        JSON = readTestResource("colors");
    }

    @Test(expected = ConverterException.class)
    public void testConvertNull() {
        ColorsConverter converter = new ColorsConverter();
        Assert.assertNull(converter.convert(null));
    }

    @Test(expected = ConverterException.class)
    public void testConvertEmpty() {
        ColorsConverter converter = new ColorsConverter();
        Assert.assertNull(converter.convert("{}"));
    }

    @Test
    public void testConvert() {
        ColorsConverter converter = new ColorsConverter();
        List<ColorResult> colorResults = converter.convert(JSON);
        Assert.assertNotNull(colorResults);
        Assert.assertEquals(2, colorResults.size());

        ColorResult colorResult = colorResults.get(0);
        Assert.assertNotNull(colorResult);
        Assert.assertEquals("http://www.stockpodium.com/stock-photo-8244245/smiling-presenting-2-apples-image.jpg",
                colorResult.getUrl());
        Info info = colorResult.getInfo();
        Assert.assertNotNull(info);
        List<Color> imageColors = info.getImageColors();
        Assert.assertNotNull(imageColors);
        Assert.assertEquals(5, imageColors.size());
        Color c1 = imageColors.get(0);
        Assert.assertNotNull(c1);
        Assert.assertEquals(38.90, c1.getPercent());
        Assert.assertEquals(247, c1.getR().longValue());
        Assert.assertEquals(245, c1.getG().longValue());
        Assert.assertEquals(243, c1.getB().longValue());
        Assert.assertEquals("#f7f5f3", c1.getHtmlCode());
        Assert.assertEquals("white", c1.getClosestPaletteColor());
        Assert.assertEquals("white", c1.getClosestPaletteColorParent());
        Assert.assertEquals(1.8304102578, c1.getClosestPaletteDistance());
    }

}
