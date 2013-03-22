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

package net.jakobnielsen.imagga.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static net.jakobnielsen.imagga.ListTools.implode;

public class APIClient {

    protected static final String TRUE_VALUE = "1";

    protected static final String FALSE_VALUE = "0";

    private final APIClientConfig apiConfig;

    private final String apiService;

    protected APIClient(APIClientConfig apiConfig, String apiService) {
        this.apiConfig = apiConfig;
        this.apiService = apiService;
    }

    String getApiUrl() {
        return "http://" + this.apiConfig.getEndpoint();
    }

    String getApiKey() {
        return this.apiConfig.getKey();
    }

    String getApiSecret() {
        return this.apiConfig.getSecret();
    }

    String getApiService() {
        return apiService;
    }

    public APIClientConfig getApiConfig() {
        return apiConfig;
    }

    public String getServerAddr() {
        return getApiUrl() + "/" + getApiService() + ".php";
    }

    protected String callMethod(Method method) throws APIClientException {
        return callMethod(method, "application/x-www-form-urlencoded");
    }

    protected String callMethod(Method method, String contentType) throws APIClientException {
        try {
            String postString = createPostString(method);

            URL url = new URL(getServerAddr());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestProperty("Charset", ApiConstants.CHARSET);
            connection.setRequestProperty("Content-Length", Integer.toString(postString.getBytes().length));
            connection.setRequestProperty("User-Agent", "Crop & Slice API JAVA Client");
            connection.setUseCaches(false);
            connection.setConnectTimeout(apiConfig.getConnectionTimeout());
            connection.setReadTimeout(apiConfig.getReadTimeout());

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(postString);
            wr.flush();
            wr.close();

            InputStream in = null;
            InputStreamReader is = null;
            BufferedReader br = null;

            try {
                if (connection.getResponseCode() == 200) {
                    in = connection.getInputStream();
                    is = new InputStreamReader(in);
                    StringBuilder sb = new StringBuilder();
                    br = new BufferedReader(is);
                    String read = br.readLine();
                    while (read != null) {
                        sb.append(read);
                        read = br.readLine();
                    }
                    return sb.toString();
                } else {
                    throw new IOException(
                            "Error " + connection.getResponseCode() + " : " + connection.getResponseMessage());
                }
            } finally {
                connection.disconnect();
                if (in != null) {
                    try { in.close(); } catch (IOException e) { /* Ignored since there is no recovery needed */ }
                }
                if (is != null) {
                    try { is.close(); } catch (IOException e) { /* Ignored since there is no recovery needed */ }
                }
                if (br != null) {
                    try { br.close(); } catch (IOException e) { /* Ignored since there is no recovery needed */ }
                }
            }

        } catch (SocketTimeoutException e) {
            throw new APIClientException("Connection to " + getServerAddr() + " timed out.", e);
        } catch (IOException e) {
            throw new APIClientException(e.getMessage(), e);
        }
    }

    /*
      http://imagga.com/api/docs/how-to-generate-api-call-signature.html
     */
    String generateSig(Map<String, List<String>> params) {

        StringBuffer str = new StringBuffer();
        List<String> sortedKeys = new ArrayList<String>(params.keySet());

        Collections.sort(sortedKeys);

        for (String s : sortedKeys) {
            str.append(s).append("=").append(implode(params.get(s)));
        }
        str.append(getApiSecret());

        return md5(str);
    }

    String createPostString(Method method) throws UnsupportedEncodingException {
        List<String> postParams = new ArrayList<String>();

        method.addParam(ApiConstants.API_KEY_PARAM, getApiKey());
        if (!method.hasParam(ApiConstants.VERSION_PARAM)) {
            method.addParam(ApiConstants.VERSION_PARAM, "1.0");
        }
        for (String name : method.getParams().keySet()) {
            List<String> paramList = method.getParam(name);
            postParams.add(name + "=" + URLEncoder.encode(implode(paramList), ApiConstants.CHARSET));
        }
        postParams.add(ApiConstants.SIGNATURE_PARAM + "=" + generateSig(method.getParams()));
        return implode(postParams, "&");
    }

    private String md5(StringBuffer str) {
        StringWriter result = new StringWriter();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.toString().getBytes(ApiConstants.CHARSET));
            byte[] hashValue = md5.digest();
            for (byte aHashValue : hashValue) {
                String word = Integer.toString(aHashValue & 0xff, 16).toUpperCase();
                if (word.length() == 1) {
                    result.write("0");
                }
                result.write(word);
            }
        } catch (UnsupportedEncodingException u) {
            return null;
        } catch (java.security.NoSuchAlgorithmException n) {
            return null;
        }
        return result.toString().toLowerCase();
    }
}
