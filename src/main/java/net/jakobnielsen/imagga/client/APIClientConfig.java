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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Imagga Configuration object.
 */
public class APIClientConfig {

    private static final int DEFAULT_TIMEOUT = 10000;

    private static final int NO_TIMEOUT = 0;

    private final String key;

    private final String secret;

    private final String endpoint;

    private final int connectionTimeout;

    private final int readTimeout;

    /**
     * Constructor
     *
     * @param key               API customer key. This is the key you've been given when creating your API account.
     * @param secret            API customer secret. This is the secret key you've been given when creating your API
     *                          account.
     * @param endpoint          API customer endpoint. This is the endpoint you've been given when creating your API
     *                          account.
     * @param connectionTimeout Sets a specified timeout value, in milliseconds, to be used when opening a
     *                          communications link to the resource referenced by the client in use. If the timeout
     *                          expires before the connection can be established,
     *                          a net.jakobnielsen.imagga.client.APIClientException
     *                          is raised in the client. A timeout of zero is interpreted as an infinite timeout.
     * @param readTimeout       Sets the read timeout to a specified timeout, in milliseconds. A non-zero value
     *                          specifies the timeout when reading from Input stream when a connection is established to
     *                          a resource. If the timeout expires before there is data available for read, a a
     *                          net.jakobnielsen.imagga.client.APIClientException is raised in the client. A timeout of
     *                          zero is interpreted as an infinite timeout.
     */
    public APIClientConfig(String key, String secret, String endpoint, Integer connectionTimeout, Integer readTimeout) {
        this.key = key;
        this.secret = secret;
        this.endpoint = endpoint;
        if (connectionTimeout != null && connectionTimeout > 0) {
            this.connectionTimeout = connectionTimeout;
        } else {
            this.connectionTimeout = NO_TIMEOUT;
        }

        if (readTimeout != null && readTimeout > 0) {
            this.readTimeout = readTimeout;
        } else {
            this.readTimeout = NO_TIMEOUT;
        }
    }

    /**
     * Constructor with default connection timeouts set to 10 seconds.
     *
     * @param key               The Imagga account key
     * @param secret            The Imagga account secret
     * @param endpoint          The Imagga account endpoint

     */
    public APIClientConfig(String key, String secret, String endpoint) {
        this.key = key;
        this.secret = secret;
        this.endpoint = endpoint;
        this.connectionTimeout = DEFAULT_TIMEOUT;
        this.readTimeout = DEFAULT_TIMEOUT;
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * Load config from ~/imagga.properties or environment variables.
     *
     * <p>Properties to be set in ~/imagga.properties is imagga.key, imagga.secret and imagga.endpoint.</p>
     *
     * <p>Environment that should be set is IMAGGA_KEY, IMAGGA_SECRET and IMAGGA_ENDPOINT.</p>
     *
     * @return A loaded config object.
     */
    public static APIClientConfig load() throws IOException {

        File propertiesFile = new File(System.getProperty("user.home") + File.separator + ".imagga");
        if (propertiesFile.exists()) {
            Properties p = new Properties();
            p.load(new FileInputStream(propertiesFile));
            return new APIClientConfig(
                    p.getProperty("imagga.key"),
                    p.getProperty("imagga.secret"),
                    p.getProperty("imagga.endpoint"),
                    Integer.valueOf(p.getProperty("imagga.connection.timeout")),
                    Integer.valueOf(p.getProperty("imagga.read.timeout")));
        } else {
            return new APIClientConfig(
                    System.getenv("IMAGGA_KEY"),
                    System.getenv("IMAGGA_SECRET"),
                    System.getenv("IMAGGA_ENDPOINT"),
                    Integer.getInteger(System.getenv("IMAGGA_CONNECTION_TIMEOUT")),
                    Integer.getInteger(System.getenv("IMAGGA_READ_TIMEOUT")));
        }
    }
}
