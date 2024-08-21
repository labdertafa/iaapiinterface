package com.laboratorio.iaapiinterface.text.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 08/08/2024
 * @updated 08/08/2024
 */
public class GPTApiException extends  RuntimeException {
    private static final Logger log = LogManager.getLogger(GPTApiException.class);

    public GPTApiException(String className, String message) {
        super(message);
        log.error(String.format("Error %s: %s", className, message));
    }
}