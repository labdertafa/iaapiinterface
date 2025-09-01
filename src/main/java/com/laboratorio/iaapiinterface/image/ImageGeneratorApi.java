package com.laboratorio.iaapiinterface.image;

import com.google.gson.Gson;
import com.laboratorio.clientapilibrary.ApiClient;
import com.laboratorio.clientapilibrary.exceptions.ApiClientException;
import com.laboratorio.clientapilibrary.model.ApiMethodType;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.clientapilibrary.model.ApiResponse;
import com.laboratorio.clientapilibrary.utils.ReaderConfig;
import com.laboratorio.iaapiinterface.image.modelo.ImageGeneratorRequest;
import com.laboratorio.iaapiinterface.image.modelo.ImagenGeneratorFile;
import com.laboratorio.iaapiinterface.image.utils.ImageGeneratorUtils;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.4
 * @created 19/08/2024
 * @updated 01/09/2025
 */
public class ImageGeneratorApi {
    protected static final Logger log = LogManager.getLogger(ImageGeneratorApi.class);
    
    private void logException(Exception e) {
        log.error("Error: " + e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: " + e.getCause().getMessage());
        }
    }
    
    private String getContentType(Map<String, List<String>> httpHeaders, String defaultContentType) {
        // Valor por defecto
        String contentType = defaultContentType;
        String headerKey = "Content-Type";

        // Verificamos si el mapa de encabezados contiene la clave "Content-Type"
        if (httpHeaders.containsKey(headerKey)) {
            List<String> contentTypeValues = httpHeaders.get(headerKey);
            if (contentTypeValues != null && !contentTypeValues.isEmpty()) {
                contentType = contentTypeValues.get(0);
                log.debug("Se encontró el Content-Type: {}", contentType);
            }
        }

        return contentType;
    }
    
    public ImagenGeneratorFile generateImage(String prompt) throws Exception {
        ApiClient client = new ApiClient();
        ImagenGeneratorFile imageFile = null;
        String generatedFilePath;
        
        try {
            ReaderConfig config = new ReaderConfig("config//ia_api_config.properties");
            String endpoint = config.getProperty("image_endpoint");
            int okResponse = Integer.parseInt(config.getProperty("image_endpoint_response"));
            String token = config.getProperty("bearer_token");
            String destinationFile = config.getProperty("temporal_image_path");
            String mediaType = config.getProperty("image_media_type");
            double guidanceScale = Double.parseDouble(config.getProperty("image_default_guidanceScale"));
            int numInferenceSteps = Integer.parseInt(config.getProperty("image_default_numInferenceSteps"));
            int height = Integer.parseInt(config.getProperty("image_default_height"));
            int width = Integer.parseInt(config.getProperty("image_default_width"));

            ImageGeneratorRequest imageRequest = new ImageGeneratorRequest(prompt, width, height, guidanceScale, numInferenceSteps);
            Gson gson = new Gson();
            String requestJson = gson.toJson(imageRequest);
            log.debug("Request a enviar: " + requestJson);
            
            ApiRequest request = new ApiRequest(endpoint, okResponse, ApiMethodType.POST, requestJson);
            request.addApiHeader("Authorization", "Bearer " + token);
            
            ApiResponse response = client.executeApiRequest(request);
            
            if (response.getResponseByte() == null) {
                log.error("No se ha podido generar la imagen. Se obtuvo una respuesta vacía");
                return null;
            }
            
            generatedFilePath = ImageGeneratorUtils.almacenarImagen(response.getResponseByte(), destinationFile);
            if (generatedFilePath != null) {
                imageFile = new ImagenGeneratorFile(generatedFilePath, this.getContentType(response.getHttpHeaders(), mediaType));
                log.info("Se ha creado correctamente la imagen solicitada a Huggingface en la ruta: {}", generatedFilePath);
            }
        } catch (ApiClientException e) {
            throw e;
        } catch (Exception e) {
            logException(e);
            throw e;
        }
        
        return imageFile;
    }
}