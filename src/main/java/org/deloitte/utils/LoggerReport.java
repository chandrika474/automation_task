package org.deloitte.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;

public class LoggerReport {
    private Logger logger;
    private final ExtentTest testLog = ExtentCucumberAdapter.getCurrentStep();

    private LoggerReport(Class<T> tClass) {
        this.logger = LogManager.getLogger(tClass);
    }

    public static LoggerReport getLogger(Class tClass) {
        return new LoggerReport(tClass);
    }

    public void info(String message) {
        logger.info(message);
        testLog.info(message);
    }

    public void debug(String message) {
        logger.debug(message);
        testLog.info(message);
    }

    public void error(String message) {
        logger.error(message);
        testLog.fail(message);
    }

    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
        testLog.fail(throwable);
    }
}
