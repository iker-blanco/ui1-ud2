package utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomLogger {

    public static Logger getLogger(Class<?> className) {
        // Instantiate a logger for the class
        Logger logger = Logger.getLogger(className.getName());

        // Instantiate a handler for the logger
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        // Use the custom formatter
        handler.setFormatter(new CustomLogFormatter());

        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        // Return the logger with all the custom settings

        return logger;
    }
}
