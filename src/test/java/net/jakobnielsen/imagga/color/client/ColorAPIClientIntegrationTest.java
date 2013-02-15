package net.jakobnielsen.imagga.color.client;

import net.jakobnielsen.imagga.client.APIClientConfig;
import net.jakobnielsen.imagga.color.bean.Color;
import net.jakobnielsen.imagga.color.bean.ColorIndexType;
import net.jakobnielsen.imagga.color.bean.ColorResult;
import net.jakobnielsen.imagga.color.bean.IndexableImage;
import net.jakobnielsen.imagga.color.bean.Info;
import net.jakobnielsen.imagga.color.bean.RankSimilarity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ColorAPIClientIntegrationTest {


    private ColorAPIClient client;

    @Before
    public void setUp() throws IOException {

        client = new ColorAPIClient(APIClientConfig.load());
    }

    @Test
    public void testColorsByUrls() {

        String url1 = "http://www.jakobnielsen.net/etc/images/cool-cartoon-291732.png";
        String url2 = "http://www.toondoo.com/public/l/a/z/lazee/toons/cool-cartoon-152229.png";

        ColorsByUrlsRequest request = new ColorsByUrlsRequest();

        request.setUrlsToProcess(Arrays.asList(
                new IndexableImage(url1, 100),
                new IndexableImage(url2, 101)));

        List<ColorResult> lst = client.colorsByUrls(request);
        Assert.assertNotNull(lst);
        Assert.assertEquals(2, lst.size());

        ColorResult cr = lst.get(0);
        Assert.assertNotNull(cr);
        Assert.assertEquals(url1, cr.getUrl());
        Info info = cr.getInfo();
        Assert.assertNotNull(info);
        Assert.assertNotNull(info.getBackgroundColors());
        Assert.assertEquals(3, info.getBackgroundColors().size());
    }

    @Test
    public void testRankSimiliarColors() throws Exception {
        RankSimilarColorRequest request = new RankSimilarColorRequest();

        request.setColorIndexType(ColorIndexType.OVERALL);
        request.setColorVector(Arrays.asList(
                new Color(60, 255, 0, 0),
                new Color(40, 0, 255, 0)
        ));
        request.setCount(10);
        request.setDist(12);

        List<RankSimilarity> lst = client.rankSimilarColor(request);
        Assert.assertNotNull(lst);

        // Its hard to test anything else but this as far as I can tell, since the returned result depends on indexed
        // images on each unique account.


    }
}
