package com.laboratorio.iaapiinterface.image.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 19/08/2024
 * @updated 19/08/2024
 */
public class ImageGeneratorApiException extends RuntimeException {
    private static final Logger log = LogManager.getLogger(ImageGeneratorApiException.class);

    public ImageGeneratorApiException(String className, String message) {
        super(message);
        log.error(String.format("Error %s: %s", className, message));
    }
}