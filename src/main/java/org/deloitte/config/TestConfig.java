package org.deloitte.config;

import org.deloitte.utils.PropertiesUtil;
import org.deloitte.utils.TestData;

import java.util.Properties;

public class TestConfig {
	private String url;
	private String gurl;
	private String apiBaseURI;
	private String browser;
	private String to;
	private String from;
	private String host;
	private String loginEmail;
	private String loginEmailPassword;
	private static TestConfig testConfig;

	public String getUrl() {
		return url;
	}
	
	public String getGurl() {
		return gurl;
	}

	public String getApiBaseURI() {
		return apiBaseURI;
	}

	public String getBrowser() {
		return browser;
	}

	public String getToEmail() {
		return to;
	}

	public String getFromEmail() {
		return from;
	}

	public String getHostEmail() {
		return host;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public String getLoginEmailPassword() {
		return loginEmailPassword;
	}

	public static synchronized TestConfig getInstance() {
		if (testConfig == null) {
			testConfig = new TestConfig();
			Properties props = new PropertiesUtil().readPropertiesFile(TestData.configFile);
			testConfig.url = props.getProperty("URL");
			testConfig.gurl = props.getProperty("GURL");
			testConfig.browser = props.getProperty("BROWSER");
			testConfig.apiBaseURI = props.getProperty("API_URL");
			testConfig.to = props.getProperty("To");
			testConfig.from = props.getProperty("From");
			testConfig.host = props.getProperty("Host");
			testConfig.loginEmail = props.getProperty("LOGIN_EMAIL");
			testConfig.loginEmailPassword = props.getProperty("LOGIN_EMAIL_PASSWORD");
		}
		return testConfig;
	}
}