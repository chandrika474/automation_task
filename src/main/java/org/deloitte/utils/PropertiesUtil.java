package org.deloitte.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private final Logger logger = LogManager.getLogger(PropertiesUtil.class);

    public Properties readPropertiesFile(String filePath) {
        logger.info("Reading the properties file located at {}", filePath);
        File file = new File(filePath);
        Properties properties = null;
        try {
            FileReader stream = new FileReader(file);
            properties = new Properties();
            properties.load(stream);
        } catch (FileNotFoundException e) {
            String message = String.format("File not at found at {}", filePath);
            logger.error(message, e);
        } catch (IOException e) {
            String message = String.format("Unable to read file due to {}", filePath);
            logger.error(message, e);
        }
        return properties;
    }
}
