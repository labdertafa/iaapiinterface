package com.laboratorio.iaapiinterface.image;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laboratorio.clientapilibrary.ApiClient;
import com.laboratorio.clientapilibrary.exceptions.ApiClientException;
import com.laboratorio.clientapilibrary.model.ApiMethodType;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.clientapilibrary.model.ApiResponse;
import com.laboratorio.iaapiinterface.image.modelo.ImageGeneratorRequest;
import com.laboratorio.iaapiinterface.image.modelo.ImageGeneratorResponse;
import com.laboratorio.iaapiinterface.image.modelo.ImageGeneratorResponseUrl;
import com.laboratorio.iaapiinterface.image.modelo.ImagenGeneratorFile;
import com.laboratorio.iaapiinterface.image.utils.ImageGeneratorUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 19/08/2024
 * @updated 18/10/2024
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
}