package com.laboratorio.iaapiinterface;

import com.laboratorio.iaapiinterface.image.ImageGeneratorApi;
import com.laboratorio.iaapiinterface.image.modelo.ImagenGeneratorFile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 21/08/2024
 * @updated 01/09/2025
 */
public class ImageGeneratorApiTest {
    @Test
    public void generateImageTest() throws Exception {
        String prompt = "Crea una imagen que se pueda asociar a información: El 3 de febrero de 2004, Facebook fue lanzado por Mark Zuckerberg y sus compañeros de cuarto de la Universidad de Harvard, cambiando para siempre la forma en que las personas se conectan y comparten información en línea.";
        
        ImageGeneratorApi api = new ImageGeneratorApi();
        ImagenGeneratorFile response = api.generateImage(prompt);

        assertNotNull(response);
    }
}