package net.jakobnielsen.imagga.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class APIClientConfig {

    private String key;

    private String secret;

    private String endpoint;

    public APIClientConfig(String key, String secret, String endpoint) {
        this.key = key;
        this.secret = secret;
        this.endpoint = endpoint;
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

    /**
     * Load config from ~/imagga.properties or environment variables.
     *
     * <p>Properties to be set in ~/imagga.properties is imagga.key, imagga.secret and imagge.endpoint.</p>
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
            return new APIClientConfig(p.getProperty("imagga.key"), p.getProperty("imagga.secret"),
                    p.getProperty("imagga.endpoint"));
        } else {
            return new APIClientConfig(System.getenv("IMAGGA_KEY"),
                    System.getenv("IMAGGA_SECRET"),
                    System.getenv("IMAGGA_ENDPOINT"));
        }
    }
}
