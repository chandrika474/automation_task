package org.deloitte.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deloitte.utils.DeloitteDriver;


public class BaseTest {
    public DeloitteDriver driver;
    public final Logger logger = LogManager.getLogger(this.getClass());
}
