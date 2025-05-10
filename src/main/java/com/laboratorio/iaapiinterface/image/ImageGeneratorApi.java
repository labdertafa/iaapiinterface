package com.laboratorio.iaapiinterface.image;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laboratorio.clientapilibrary.ApiClient;
import com.laboratorio.clientapilibrary.exceptions.ApiClientException;
import com.laboratorio.clientapilibrary.model.ApiMethodType;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.clientapilibrary.model.ApiResponse;
import com.laboratorio.clientapilibrary.utils.ReaderConfig;
import com.laboratorio.iaapiinterface.image.modelo.HuggingfaceImageRequest;
import com.laboratorio.iaapiinterface.image.modelo.HuggingfaceParameters;
import com.laboratorio.iaapiinterface.image.modelo.ImageGeneratorRequest;
import com.laboratorio.iaapiinterface.image.modelo.ImageGeneratorResponse;
import com.laboratorio.iaapiinterface.image.modelo.ImageGeneratorResponseUrl;
import com.laboratorio.iaapiinterface.image.modelo.ImagenGeneratorFile;
import com.laboratorio.iaapiinterface.image.utils.ImageGeneratorUtils;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.3
 * @created 19/08/2024
 * @updated 10/05/2025
 */
public class ImageGeneratorApi {
    protected static final Logger log = LogManager.getLogger(ImageGeneratorApi.class);
    
    private void logException(Exception e) {
        log.error("Error: " + e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: " + e.getCause().getMessage());
        }
    }
    
    private String generateImage(String endpoint, String token, ImageGeneratorRequest imageGeneratorRequest) throws Exception {
        ApiClient client = new ApiClient();
        
        try {
            Gson gson = new Gson();
            String requestJson = gson.toJson(imageGeneratorRequest);
            log.debug("Request a enviar a GPT: " + requestJson);
            
            String url = endpoint;
            ApiRequest request = new ApiRequest(url, 201, ApiMethodType.POST, requestJson);
            request.addApiHeader("Authorization", "Bearer " + token);
            
            ApiResponse response = client.executeApiRequest(request);
            
            return response.getResponseStr();
        } catch (ApiClientException e) {
            throw e;
        } catch (Exception e) {
            logException(e);
            throw e;
        }
    }
    
    private ImageGeneratorResponse generateCoded64Image(String endpoint, String token, ImageGeneratorRequest request) throws Exception {
        try {
            String responseJson = this.generateImage(endpoint, token, request);
            Gson gson = new Gson();
            return gson.fromJson(responseJson, ImageGeneratorResponse.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        }
    }
    
    private ImageGeneratorResponseUrl generarUrlImage (String endpoint, String token, ImageGeneratorRequest request) throws Exception {
        try {
            String responseJson = this.generateImage(endpoint, token, request);
            Gson gson = new Gson();
            return gson.fromJson(responseJson, ImageGeneratorResponseUrl.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        }
    }
    
    public ImagenGeneratorFile generarImagen(String endpoint, String token, ImageGeneratorRequest request, String destinationFile, String defaultMediaType) throws Exception {
        ImagenGeneratorFile imageFile = null;
        String generatedFilePath = null;
        String mediaType = defaultMediaType;
        
        try {
            switch (request.getModel()) {
                case "flux/schnell", "flux-pro", "flux/dev", "flux-realism" -> {
                    ImageGeneratorResponseUrl responseUrl = this.generarUrlImage(endpoint, token, request);
                    log.info("La imagen ha sido generada por la IA");
                    generatedFilePath = ImageGeneratorUtils.downloadImage(responseUrl.getImages().get(0).getUrl(), destinationFile);
                    if (generatedFilePath != null) {
                        log.info("La imagen generada por la IA ha sido almacenada en el fichero " + destinationFile);
                        mediaType = responseUrl.getImages().get(0).getContent_type();
                        log.info("El media type de la imagen generada por la IA es " + mediaType);
                    }
                }
                default -> {
                    ImageGeneratorResponse response = this.generateCoded64Image(endpoint, token, request);
                    generatedFilePath = ImageGeneratorUtils.decodificarImagenBase64(response.getOutput().getChoices().get(0).getImage_base64(), destinationFile);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        
        if (generatedFilePath != null) {
            imageFile = new ImagenGeneratorFile(generatedFilePath, mediaType);
            log.info("Se ha creado correctamente el ImagenGeneratorFile con la información de la imagen generada");
        }
        
        
        return imageFile;
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
    
    public ImagenGeneratorFile generarHuggingfaceImage(String prompt) throws Exception {
        ApiClient client = new ApiClient();
        ImagenGeneratorFile imageFile = null;
        String generatedFilePath;
        
        try {
            ReaderConfig config = new ReaderConfig("config//ia_api_config.properties");
            String endpoint = config.getProperty("image_endpoint");
            int okResponse = Integer.parseInt(config.getProperty("image_endpoint_response"));
            String token = config.getProperty("image_token");
            String destinationFile = config.getProperty("temporal_image_path");
            String mediaType = config.getProperty("image_media_type");
            double guidanceScale = Double.parseDouble(config.getProperty("image_default_guidanceScale"));
            int numInferenceSteps = Integer.parseInt(config.getProperty("image_default_numInferenceSteps"));
            int height = Integer.parseInt(config.getProperty("image_default_height"));
            int width = Integer.parseInt(config.getProperty("image_default_width"));
            
            HuggingfaceParameters parameters = new HuggingfaceParameters(guidanceScale, numInferenceSteps, height, width);
            HuggingfaceImageRequest imageRequest = new HuggingfaceImageRequest(prompt, parameters);
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