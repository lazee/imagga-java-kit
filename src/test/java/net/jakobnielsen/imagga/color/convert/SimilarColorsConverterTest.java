package net.jakobnielsen.imagga.color.convert;

import junit.framework.Assert;
import net.jakobnielsen.imagga.client.AbstractConverterTester;
import net.jakobnielsen.imagga.color.bean.ColorResult;
import net.jakobnielsen.imagga.color.bean.RankSimilarity;
import net.jakobnielsen.imagga.convert.ConverterException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SimilarColorsConverterTest extends AbstractConverterTester {

    private String JSON;

    @Before
    public void setUp() throws Exception {
        JSON = readTestResource("similarcolors");
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
        SimilarColorsConverter converter = new SimilarColorsConverter();
        List<RankSimilarity> rankSimilarities = converter.convert(JSON);
        Assert.assertNotNull(rankSimilarities);
        Assert.assertEquals(2, rankSimilarities.size());

        RankSimilarity r0 = rankSimilarities.get(0);
        Assert.assertNotNull(r0);
        Assert.assertNotNull(r0.getId());
        Assert.assertNotNull(r0.getDist());
        Assert.assertEquals(8774077, r0.getId().longValue());
        Assert.assertEquals(2597.38299, r0.getDist());

        RankSimilarity r1 = rankSimilarities.get(1);
        Assert.assertNotNull(r1);
        Assert.assertNotNull(r1.getId());
        Assert.assertNotNull(r1.getDist());
        Assert.assertEquals(9085916, r1.getId().longValue());
        Assert.assertEquals(2681.33259, r1.getDist());
    }
}
