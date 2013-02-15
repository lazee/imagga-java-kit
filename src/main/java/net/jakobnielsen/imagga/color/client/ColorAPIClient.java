package net.jakobnielsen.imagga.color.client;

import net.jakobnielsen.imagga.client.APIClient;
import net.jakobnielsen.imagga.client.APIClientConfig;
import net.jakobnielsen.imagga.client.ApiConstants;
import net.jakobnielsen.imagga.client.Method;
import net.jakobnielsen.imagga.color.bean.Color;
import net.jakobnielsen.imagga.color.bean.ColorResult;
import net.jakobnielsen.imagga.color.bean.IndexableImage;
import net.jakobnielsen.imagga.color.bean.RankSimilarity;
import net.jakobnielsen.imagga.color.convert.ColorsConverter;
import net.jakobnielsen.imagga.color.convert.SimilarColorsConverter;

import java.util.ArrayList;
import java.util.List;

public class ColorAPIClient extends APIClient {

    protected ColorAPIClient(APIClientConfig apiConfig) {
        super(apiConfig, "colorsearchserver");
    }

    protected String colorsByUrlsAsJson(ColorsByUrlsRequest request) {
        Method method = new Method("imagga.colorseach.extract");

        List<String> urls = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();
        for (IndexableImage iu : request.getUrlsToProcess()) {
            urls.add(iu.getUrl());
            if (iu.getId() != null) {
                ids.add(iu.getId().toString());
            }
        }
        method.addParam(ApiConstants.URLS, urls);
        if (ids != null && ids.size() > 0) {
            method.addParam("ids", ids);
        }
        method.addParam("extract_overall_colors", request.isExtractOverallColors() ? "1" : "0");
        method.addParam("extract_object_colors", request.isExtractObjectColors() ? "1" : "0");
        return callMethod(method);
    }


    public List<ColorResult> colorsByUrls(ColorsByUrlsRequest request) {
        ColorsConverter converter = new ColorsConverter();
        return converter.convert(colorsByUrlsAsJson(request));
    }

    protected String rankSimilarColorAsJson(RankSimilarColorRequest request) {
        Method method = new Method("imagga.colorseach.rank");

        List<String> colorVector = new ArrayList<String>();
        for (Color c : request.getColorVector()) {
            colorVector.add(c.getPercent().toString() + "," + c.getR() + "," + c.getG() + "," + +c.getB() + ",");
        }
        method.addParam("color_vector", colorVector);
        method.addParam("type", request.getColorIndexType().getName());
        method.addParam("dist", request.getDist().toString());
        method.addParam("count", request.getCount().toString());

        return callMethod(method);
    }

    public List<RankSimilarity> rankSimilarColor(RankSimilarColorRequest request) {
        SimilarColorsConverter converter = new SimilarColorsConverter();
        return converter.convert(rankSimilarColorAsJson(request));
    }

}
