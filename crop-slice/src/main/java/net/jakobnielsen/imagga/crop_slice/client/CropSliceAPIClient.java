package net.jakobnielsen.imagga.crop_slice.client;

import net.jakobnielsen.imagga.client.APIClient;
import net.jakobnielsen.imagga.client.ApiConstants;
import net.jakobnielsen.imagga.client.Method;
import net.jakobnielsen.imagga.crop_slice.bean.ApiUsage;
import net.jakobnielsen.imagga.crop_slice.bean.DivisionRegion;
import net.jakobnielsen.imagga.crop_slice.bean.SmartCropping;
import net.jakobnielsen.imagga.crop_slice.convert.ApiUsageConverter;
import net.jakobnielsen.imagga.crop_slice.convert.DivisionRegionConverter;
import net.jakobnielsen.imagga.crop_slice.convert.SmartCroppingConverter;

import java.util.List;

public class CropSliceAPIClient extends APIClient {

    private static final String METHOD_PROCESS_CROP = "imagga.process.crop";

    private static final String METHOD_PROCESS_DIVISION = "imagga.process.division";

    private static final String METHOD_USAGE_GET = "imagga.usage.get";

    private static final String RESOLUTIONS = "resolutions";

    private static final String NO_SCALING = "no_scaling";

    private static final String START = "start";

    private static final String END = "end";

    public CropSliceAPIClient(String apiKey, String apiSecret, String apiEndpoint) {
        super(apiKey, apiSecret, apiEndpoint, "extractionrestserver");
    }

    /**
     * @param urlsToProcess is an array of public image URLs to be processed for rectangular regions extraction, up to
     *                      10 URLs can be submitted for processing in a single call. You get a result array with
     *                      entries in the same order the URL have been given for processing, each entry contains the
     *                      URL and an array of rectangular regions defined by x1, y1, x2, y2. If there is an error with
     *                      processing the image instead of an array with regions you get the string "ERROR loading
     *                      URL"
     */
    public List<DivisionRegion> divisionRegionsByUrls(List<String> urlsToProcess) {
        DivisionRegionConverter converter = new DivisionRegionConverter();
        Method method = new Method(METHOD_PROCESS_DIVISION);
        method.addParam(ApiConstants.URLS, urlsToProcess);
        return converter.convert(callMethod(method));
    }

    /**
     * Get the smart cropping coordinates from the Imagga service for a given list of images.
     *
     * @param urlsToProcess
     * @param resolutionsList
     * @param noScaling
     * @return
     */
    public List<SmartCropping> smartCroppingByUrls(List<String> urlsToProcess, List<String> resolutionsList, boolean noScaling) {
        SmartCroppingConverter converter = new SmartCroppingConverter();
        Method method = new Method(METHOD_PROCESS_CROP);
        method.addParam(ApiConstants.URLS, urlsToProcess);
        method.addParam(RESOLUTIONS, resolutionsList);
        method.addParam(NO_SCALING, noScaling ? "1" : "0");
        return converter.convert(callMethod(method));
    }

    /**
     * Get an usage report for your Imagga account
     *
     * NOTE : If timestamp parameters are set to 0 the API usage since the beginning of current months is returned,
     *
     * @param startTime The wanted start timestamp of the usage report (seconds since 1970).
     * @param endTime   The wanted end timestamp of the usage report (seconds since 1970).
     * @return ApiUsage object with fetched data.
     * @throws net.jakobnielsen.imagga.ImaggaException If data couldn't be fetched from the Imagga service or if parsing
     * failed.
     */
    public ApiUsage apiUsage(long startTime, long endTime) {
        ApiUsageConverter converter = new ApiUsageConverter();
        Method method = new Method(METHOD_USAGE_GET);
        method.addParam(START, Long.toString(startTime));
        method.addParam(END, Long.toString(endTime));
        return converter.convert(callMethod(method));
    }

}
