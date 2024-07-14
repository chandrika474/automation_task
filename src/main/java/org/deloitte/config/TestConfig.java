package org.deloitte.config;

import org.deloitte.utils.PropertiesUtil;
import org.deloitte.utils.TestData;

import java.util.Properties;

public class TestConfig {
    private String url;
    private String apiBaseURI;
    private String browser;
    private static TestConfig testConfig;

    public String getUrl() {
        return url;
    }

    public String getApiBaseURI() {
        return apiBaseURI;
    }

    public String getBrowser() {
        return browser;
    }

    public static synchronized TestConfig getInstance() {
        if (testConfig == null) {
            testConfig = new TestConfig();
            Properties props = new PropertiesUtil().readPropertiesFile(TestData.configFile);
            testConfig.url = props.getProperty("URL");
            testConfig.browser = props.getProperty("BROWSER");
            testConfig.apiBaseURI = props.getProperty("API_URL");
        }
        return testConfig;
    }
}