package com.laboratorio.iaapiinterface.utils;

import java.io.FileReader;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 28/07/2024
 * @updated 21/08/2024
 */
public class IAApiInterfaceConfig {
    private static final Logger log = LogManager.getLogger(IAApiInterfaceConfig.class);
    private static IAApiInterfaceConfig instance;
    private final Properties properties;

    private IAApiInterfaceConfig() {
        this.properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try {
            this.properties.load(new FileReader("config//config.properties"));
        } catch (Exception e) {
            log.error("Ha ocurrido un error leyendo el fichero de configuración. Finaliza la aplicación!");
            log.error(String.format("Error: %s", e.getMessage()));
            if (e.getCause() != null) {
                log.error(String.format("Causa: %s", e.getCause().getMessage()));
            }
            System.exit(-1);
        }
    }

    public static IAApiInterfaceConfig getInstance() {
        if (instance == null) {
            synchronized (IAApiInterfaceConfig.class) {
                if (instance == null) {
                    instance = new IAApiInterfaceConfig();
                }
            }
        }
        
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}