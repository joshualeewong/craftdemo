package com.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {
	private static final Logger logger = Logger.getLogger("DEMO LOGS");

	private MyLogger() {
		throw new IllegalStateException("Utility class");
	}

	static {
		logger.setLevel(Level.INFO);
	}

	public static Logger getLogger() {
		return logger;
	}
}
