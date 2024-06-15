package org.deloitte.utils;

import org.deloitte.config.TestConfig;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TestData {
    private static final String rootDir = System.getProperty("user.dir");
    private static final String separator = File.separator;
    public static final String configFile = rootDir + separator + "src" + separator + "main" + separator + "resources" + separator + "config.properties";
    public static final String excelFile = rootDir + separator + "src" + separator + "main" + separator + "resources" + separator + "Deloitte.xlsx";
    public static final TestConfig testConfig = TestConfig.getInstance();
    public static final List<Map<String, String>> searchTestData = new ExcelFileUtil().readData(excelFile, "Sheet1");
}
