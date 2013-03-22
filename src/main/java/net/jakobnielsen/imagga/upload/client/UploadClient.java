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

package net.jakobnielsen.imagga.upload.client;

import net.jakobnielsen.imagga.client.APIClient;
import net.jakobnielsen.imagga.client.APIClientConfig;
import net.jakobnielsen.imagga.client.ApiConstants;
import net.jakobnielsen.imagga.upload.convert.UploadConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadClient extends APIClient {

    private static final String LINE_FEED = "\r\n";

    public UploadClient(APIClientConfig apiConfig) {
        super(apiConfig, "uploadforprocessing");
    }

    public String uploadForProcessing(File imageFile) throws IOException {
        String boundary = "===" + System.currentTimeMillis() + "===";
        URL url = new URL(getServerAddr());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.setRequestProperty("Charset", ApiConstants.CHARSET);
        connection.setRequestProperty("User-Agent", "Crop & Slice API JAVA Client");
        connection.setUseCaches(false);
        connection.setConnectTimeout(getApiConfig().getConnectionTimeout());
        connection.setReadTimeout(getApiConfig().getReadTimeout());
        OutputStream outputStream = connection.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, ApiConstants.CHARSET), true);

        // Add file
        String fileName = imageFile.getName();
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"").append(LINE_FEED);
        writer.append("Content-Type: " +  connection.guessContentTypeFromName(fileName)).append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();
        FileInputStream inputStream = new FileInputStream(imageFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();
        writer.append(LINE_FEED);
        writer.flush();
        // End of add file

        StringBuffer response = new StringBuffer();
        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();

        // checks server's status code first
        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }
        UploadConverter converter = new UploadConverter();
        return converter.convert(response.toString());
    }

}
