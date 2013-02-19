/*
 * Copyright 2013 Jakob Vad Nielsen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.jakobnielsen.imagga.crop_slice.client;

import net.jakobnielsen.imagga.ListTools;
import net.jakobnielsen.imagga.client.APIClient;
import net.jakobnielsen.imagga.client.APIClientConfig;
import net.jakobnielsen.imagga.client.ApiConstants;
import net.jakobnielsen.imagga.client.Method;
import net.jakobnielsen.imagga.crop_slice.bean.ApiUsage;
import net.jakobnielsen.imagga.crop_slice.bean.DivisionRegion;
import net.jakobnielsen.imagga.crop_slice.bean.Resolution;
import net.jakobnielsen.imagga.crop_slice.bean.SmartCropping;
import net.jakobnielsen.imagga.crop_slice.convert.ApiUsageConverter;
import net.jakobnielsen.imagga.crop_slice.convert.DivisionRegionConverter;
import net.jakobnielsen.imagga.crop_slice.convert.SmartCroppingConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CropSliceAPIClient extends APIClient {

    public CropSliceAPIClient(APIClientConfig apiConfig) {
        super(apiConfig, "extractionrestserver");
    }

    /**
     * @param urlsToProcess is an array of public image URLs to be processed for rectangular regions extraction, up to
     *                      10 URLs can be submitted for processing in a single call. You get a result array with
     *                      entries in the same order the URL have been given for processing, each entry contains the
     *                      URL and an array of rectangular regions defined by x1, y1, x2, y2. If there is an error with
     *                      processing the image instead of an array with regions you get the string "ERROR loading
     *                      URL"
     * @return List of division regions for the given list of urls.
     * @throws net.jakobnielsen.imagga.ImaggaException If data couldn't be fetched from the Imagga service or if parsing
     * failed.
     */
    public List<DivisionRegion> divisionRegionsByUrls(List<String> urlsToProcess) {
        DivisionRegionConverter converter = new DivisionRegionConverter();
        Method method = new Method("imagga.process.division");
        method.addParam(ApiConstants.URLS, urlsToProcess);
        return converter.convert(callMethod(method));
    }

    /**
     * Get the smart cropping coordinates from the Imagga service for a given list of images.
     *
     * @param urlsToProcess List of (one or more) public image URLs to be smart-cropped.
     * @param resolutionsList  list of (one or more) width and height pairs defining the target size
     *                         (the size that the images should be resized to).
     *                          If this parameter is set to <code>null</code> the
     *                         system will suggest croppings that cover the main parts of what is considered visually
     *                         interesting in the given images.
     * @param noScaling If set to <code>true</code> the system will keep strictly to the specified resolutions and
     *                  not only to their aspect ratios. Otherwise, if its value is <code>false</code>,
     *                  the system actually suggests the maximum rectangle with the same aspect ratio as the required
     *                  resolution, so that the API user will practically get a combination of cropping and
     *                  thumbnailing at the same time.
     * @return List of smart cropping results.
     * @throws net.jakobnielsen.imagga.ImaggaException If data couldn't be fetched from the Imagga service or if parsing
     * failed.
     */
    public List<SmartCropping> smartCroppingByUrls(List<String> urlsToProcess, List<Resolution> resolutionsList,
            boolean noScaling) {
        SmartCroppingConverter converter = new SmartCroppingConverter();
        Method method = new Method("imagga.process.crop");
        method.addParam(ApiConstants.URLS, urlsToProcess);
        if (resolutionsList != null) {
            method.addParam("resolutions", ListTools.implode(resolutionsList));
        }
        method.addParam("no_scaling", noScaling ? TRUE_VALUE : FALSE_VALUE);
        return converter.convert(callMethod(method));
    }

    /**
     * Get the smart cropping coordinates from the Imagga service for a given image url.
     *
     * @param urlToProcess A public image URL to be smart-cropped.
     * @param resolution  A width and height pair defining the target size (the size that the images should be
     *                    resized to). If this parameter is set to <code>null</code> the system will suggest
     *                    croppings that cover the main parts of what is considered visually interesting in the given
     *                    images.
     * @param noScaling If set to <code>true</code> the system will keep strictly to the specified resolutions and
     *                  not only to their aspect ratios. Otherwise, if its value is <code>false</code>,
     *                  the system actually suggests the maximum rectangle with the same aspect ratio as the required
     *                  resolution, so that the API user will practically get a combination of cropping and
     *                  thumbnailing at the same time.
     * @return List of smart cropping results.
     * @throws net.jakobnielsen.imagga.ImaggaException If data couldn't be fetched from the Imagga service or if parsing
     * failed.
     */
    public List<SmartCropping> smartCroppingByUrl(String urlToProcess, Resolution resolution, boolean noScaling) {
        if (resolution == null) {
            return smartCroppingByUrls(Arrays.asList(urlToProcess), null, noScaling);
        }
        return smartCroppingByUrls(Arrays.asList(urlToProcess), Arrays.asList(resolution), noScaling);
    }

    /**
     * Get the smart cropping coordinates from the Imagga service for a given image url.
     *
     * @param urlToProcess    A public image URL to be smart-cropped.
     * @param resolutionsList list of (one or more) width and height pairs defining the target size (the size that the
     *                        images should be resized to). If this parameter is set to <code>null</code> the system
     *                        will suggest croppings that cover the main parts of what is considered visually
     *                        interesting in the given images.
     * @param noScaling       If set to <code>true</code> the system will keep strictly to the specified resolutions and
     *                        not only to their aspect ratios. Otherwise, if its value is <code>false</code>, the system
     *                        actually suggests the maximum rectangle with the same aspect ratio as the required
     *                        resolution, so that the API user will practically get a combination of cropping and
     *                        thumbnailing at the same time.
     * @return List of smart cropping results.
     * @throws net.jakobnielsen.imagga.ImaggaException If data couldn't be fetched from the Imagga service or if parsing
     * failed.
     */
    public List<SmartCropping> smartCroppingByUrl(String urlToProcess, List<Resolution> resolutionsList,
            boolean noScaling) {
        return smartCroppingByUrls(Arrays.asList(urlToProcess), resolutionsList, noScaling);
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
        Method method = new Method("imagga.usage.get");
        method.addParam("start", Long.toString(startTime));
        method.addParam("end", Long.toString(endTime));
        return converter.convert(callMethod(method));
    }

}
