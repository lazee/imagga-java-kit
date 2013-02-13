package net.jakobnielsen.imagga.color.client;

import net.jakobnielsen.imagga.client.APIClient;
import net.jakobnielsen.imagga.client.ApiConstants;
import net.jakobnielsen.imagga.client.Method;
import net.jakobnielsen.imagga.color.bean.ColorResult;
import net.jakobnielsen.imagga.color.convert.ColorsConverter;

import java.util.List;

public class ColorAPIClient extends APIClient {

    protected ColorAPIClient(String apiKey, String apiSecret, String apiEndpoint) {
        super(apiKey, apiSecret, apiEndpoint, "colorsearchserver");
    }

    /**
     * @param urlsToProcess        Comma-separated list of (one or more) public image URLs to be processed for color
     *                             extraction. Example Value:
     *                             http://www.stockpodium.com/stock-photo-8244245/smiling-presenting-2-apples-image.jpg,
     *                             http://www.stockpodium.com/stock-photo-9289532/car-dirving-very-fast-under-image.jpg
     * @return List of color results from the processed images.
     */
    public List<ColorResult> colorsByUrls(List<String> urlsToProcess) {
        return colorsByUrls(urlsToProcess, null, true, true);
    }

    /**
     * @param urlsToProcess        Comma-separated list of (one or more) public image URLs to be processed for color
     *                             extraction. Example Value:
     *                             http://www.stockpodium.com/stock-photo-8244245/smiling-presenting-2-apples-image.jpg,
     *                             http://www.stockpodium.com/stock-photo-9289532/car-dirving-very-fast-under-image.jpg
     * @param ids                  Comma-separated list of (one or more) numerical ids to serve as unique identifiers of
     *                             the images whose color info should be stored in the index. The ids should be listed
     *                             in the same order as the corresponding images in the urls parameter explained above.
     *                             If this parameter is omitted or some of the ids are 0 (zero) the extracted info for
     *                             the corresponding images is NOT stored in the API customer color search index.
     *                             Example Value: 8244245,0
     * @return List of color results from the processed images.
     */
    public List<ColorResult> colorsByUrls(List<String> urlsToProcess, List<String> ids) {
        return colorsByUrls(urlsToProcess, ids, true, true);
    }

    /**
     * @param urlsToProcess        Comma-separated list of (one or more) public image URLs to be processed for color
     *                             extraction. Example Value:
     *                             http://www.stockpodium.com/stock-photo-8244245/smiling-presenting-2-apples-image.jpg,
     *                             http://www.stockpodium.com/stock-photo-9289532/car-dirving-very-fast-under-image.jpg
     * @param ids                  Comma-separated list of (one or more) numerical ids to serve as unique identifiers of
     *                             the images whose color info should be stored in the index. The ids should be listed
     *                             in the same order as the corresponding images in the urls parameter explained above.
     *                             If this parameter is omitted or some of the ids are 0 (zero) the extracted info for
     *                             the corresponding images is NOT stored in the API customer color search index.
     *                             Example Value: 8244245,0
     * @param extractOverallColors Specify if the overall image colors should be extracted. Use 'true' if not sure.
     * @param extractObjectColors  Specify if the service should try to extract object and non-object (a.k.a. foreground
     *                             and background) colors separately. Use 'true' if not sure.
     * @return List of color results from the processed images.
     */
    public List<ColorResult> colorsByUrls(List<String> urlsToProcess, List<String> ids, boolean extractOverallColors,
            boolean extractObjectColors) {
        ColorsConverter converter = new ColorsConverter();
        Method method = new Method("imagga.colorseach.extract");
        method.addParam(ApiConstants.URLS, urlsToProcess);
        if (ids != null && ids.size() > 0) {
            method.addParam("ids", ids);
        }
        method.addParam("extract_overall_colors", extractOverallColors ? "1" : "0");
        method.addParam("extract_object_colors", extractObjectColors ? "1" : "0");
        return converter.convert(callMethod(method));
    }
}
