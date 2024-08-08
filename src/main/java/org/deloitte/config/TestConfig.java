package org.deloitte.config;

import org.deloitte.pages.FlightBookingPage;
import org.deloitte.pages.FlightSearchResultsPage;
import org.deloitte.pages.IxigoHomePage;
import org.deloitte.utils.PropertiesUtil;
import org.deloitte.utils.TestData;

import java.util.Properties;

public class TestConfig {
	private String url;
	private String gurl;
	private String apiBaseURI;
	private String apiBaseURIGo;
	private String browser;
	private String to;
	private String from;
	private String host;
	private String loginEmail;
	private String loginEmailPassword;
	private Boolean headlessMode;
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
	
	public String getApiBaseURIGo() {
		return apiBaseURIGo;
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
	
	public Boolean getheadlessMode() {
		return headlessMode;
	}
	

	public static synchronized TestConfig getInstance() {
		if (testConfig == null) {
			testConfig = new TestConfig();
			Properties props = new PropertiesUtil().readPropertiesFile(TestData.configFile);
			testConfig.url = props.getProperty("URL");
			testConfig.gurl = props.getProperty("GURL");
			testConfig.browser = props.getProperty("BROWSER");
			testConfig.apiBaseURI = props.getProperty("API_URL");
			testConfig.apiBaseURIGo = props.getProperty("API_URLGo");
			testConfig.to = props.getProperty("To");
			testConfig.from = props.getProperty("From");
			testConfig.host = props.getProperty("Host");
			testConfig.loginEmail = props.getProperty("LOGIN_EMAIL");
			testConfig.loginEmailPassword = props.getProperty("LOGIN_EMAIL_PASSWORD");
			testConfig.headlessMode = Boolean.parseBoolean(props.getProperty("HEADLESSMODE"));
		}
		return testConfig;
	}
}