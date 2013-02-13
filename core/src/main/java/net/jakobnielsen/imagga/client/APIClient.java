package net.jakobnielsen.imagga.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class APIClient {

    private final String apiKey;

    private final String apiSecret;

    private final String apiEndpoint;

    private final String apiService;

    protected APIClient(String apiKey, String apiSecret, String apiEndpoint, String apiService) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.apiEndpoint = apiEndpoint;
        this.apiService = apiService;
    }

    String getApiUrl() {
        return "http://" + this.apiEndpoint;
    }

    String getApiKey() {
        return apiKey;
    }

    String getApiSecret() {
        return apiSecret;
    }

    String getApiService() {
        return apiService;
    }

    String getServerAddr() {
        return getApiUrl() + "/" + getApiService() + ".php";
    }

    protected String callMethod(Method method) throws APIClientException {
        try {
            String postString = createPostString(method);

            URL url = new URL(getServerAddr());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", ApiConstants.CHARSET);
            connection.setRequestProperty("Content-Length", "" + Integer.toString(postString.getBytes().length));
            connection.setRequestProperty("User-Agent", "Crop & Slice API JAVA Client");
            connection.setUseCaches(false);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(postString);
            wr.flush();
            wr.close();

            try {
                if (connection.getResponseCode() == 200) {
                    InputStream in = connection.getInputStream();
                    InputStreamReader is = new InputStreamReader(in);
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(is);
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
            }

        } catch (IOException e) {
            throw new APIClientException(e.getMessage(), e);
        }
    }

    String generateSig(Map<String, List<String>> params) {

        StringBuffer str = new StringBuffer();
        List<String> sortedKeys = new ArrayList<String>(params.keySet());

        // We need to sort all parameters alphabetically in order to come up with the same signature as Imagga does
        // on the server side.
        Collections.sort(sortedKeys);

        // Note: here we assume that the signature parameter is not already included in params array.
        for (String s : sortedKeys) {
            str.append(s).append("=").append(implode(params.get(s), ","));
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
            postParams.add(name + "=" + URLEncoder.encode(implode(paramList, ","), ApiConstants.CHARSET));
        }
        postParams.add(ApiConstants.SIGNATURE_PARAM + "=" + generateSig(method.getParams()));
        return implode(postParams, "&");
    }

    String implode(List<String> list, String delimiter) {
        if (list == null) {
            throw new IllegalArgumentException("List argument is null");
        } else if (delimiter == null) {

        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(delimiter == null ? "," : delimiter);
            }
        }
        return sb.toString();
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
