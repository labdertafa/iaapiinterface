package com.laboratorio.iaapiinterface;

import com.laboratorio.clientapilibrary.utils.ReaderConfig;
import com.laboratorio.iaapiinterface.image.ImageGeneratorApi;
import com.laboratorio.iaapiinterface.image.modelo.HuggingfaceImageRequest;
import com.laboratorio.iaapiinterface.image.modelo.ImageGeneratorRequest;
import com.laboratorio.iaapiinterface.image.modelo.ImagenGeneratorFile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 21/08/2024
 * @updated 10/05/2025
 */
public class ImageGeneratorApiTest {
    /* @Test
    public void generarImagenBase64() throws Exception {
        String prompt = "Crea una imagen inspirada en esta información: El 19 de agosto de 1982, la primera computadora personal, la Commodore 64, fue presentada al público, revolucionando el acceso a la tecnología en los hogares. #UnDiaComoHoy #Tecnologia";
        ReaderConfig config = new ReaderConfig("config//ia_api_config.properties");
        String token = config.getProperty("bearer_token");
        String rutaFichero = config.getProperty("temporal_image_path");
        String mediaType = config.getProperty("image_media_type");
        String endpoint = config.getProperty("image_endpoint");
        ImageGeneratorRequest request = new ImageGeneratorRequest(prompt, "runwayml/stable-diffusion-v1-5", 50, "512x512");
        
        ImageGeneratorApi api = new ImageGeneratorApi();
        ImagenGeneratorFile response = api.generarImagen(endpoint, token, request, rutaFichero, mediaType);
        
        assertTrue(response != null);
    }
    
    @Test
    public void generarImagenUrl() throws Exception {
        String prompt = "Crea una imagen inspirada en esta información: El 21 de agosto de 1994, se celebró el Gran Premio de Bélgica en Spa-Francorchamps, donde Damon Hill logró su primera victoria en F1, marcando un hito en su carrera y en la historia del campeonato. #UnDiaComoHoy #Formula1 #F1";
        ReaderConfig config = new ReaderConfig("config//ia_api_config.properties");
        String token = config.getProperty("bearer_token");
        String rutaFichero = config.getProperty("temporal_image_path");
        String mediaType = config.getProperty("image_media_type");
        String endpoint = config.getProperty("image_endpoint");
        ImageGeneratorRequest request = new ImageGeneratorRequest(prompt, "flux-realism", 50, "512x512");
        
        ImageGeneratorApi api = new ImageGeneratorApi();
        ImagenGeneratorFile response = api.generarImagen(endpoint, token, request, rutaFichero, mediaType);
        
        assertTrue(response != null);
    } */
    
    @Test
    public void generarHuggingfaceImageTest() throws Exception {
        String prompt = "Crea una imagen que se pueda asociar a información: El 3 de febrero de 2004, Facebook fue lanzado por Mark Zuckerberg y sus compañeros de cuarto de la Universidad de Harvard, cambiando para siempre la forma en que las personas se conectan y comparten información en línea.";
        
        ImageGeneratorApi api = new ImageGeneratorApi();
        ImagenGeneratorFile response = api.generarHuggingfaceImage(prompt);
        
        assertNotNull(response);
    }
}