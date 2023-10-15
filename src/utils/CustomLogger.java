package utils;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomLogger {

    public static Logger getLogger(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz.getName());

        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new CustomLogFormatter());

        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        return logger;
    }
}
