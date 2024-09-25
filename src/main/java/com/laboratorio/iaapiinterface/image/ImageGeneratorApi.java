package com.laboratorio.iaapiinterface.image;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laboratorio.iaapiinterface.image.exception.ImageGeneratorApiException;
import com.laboratorio.iaapiinterface.image.modelo.ImageGeneratorRequest;
import com.laboratorio.iaapiinterface.image.modelo.ImageGeneratorResponse;
import com.laboratorio.iaapiinterface.image.modelo.ImageGeneratorResponseUrl;
import com.laboratorio.iaapiinterface.image.modelo.ImagenGeneratorFile;
import com.laboratorio.iaapiinterface.image.utils.ImageGeneratorUtils;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 19/08/2024
 * @updated 25/09/2024
 */
public class ImageGeneratorApi {
    protected static final Logger log = LogManager.getLogger(ImageGeneratorApi.class);
    
    private void logException(Exception e) {
        log.error("Error: " + e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: " + e.getCause().getMessage());
        }
    }
    
    private String generateImage(String endpoint, String token, ImageGeneratorRequest request) throws Exception {
        Client client = ClientBuilder.newClient();
        Response response = null;
        
        try {
            Gson gson = new Gson();
            String requestJson = gson.toJson(request);
            log.debug("Request a enviar a GPT: " + requestJson);
            
            String url = endpoint;
            WebTarget target = client.target(url);
            
            response = target.request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .post(Entity.entity(requestJson, MediaType.APPLICATION_JSON));
            
            String responseJson = response.readEntity(String.class);
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                log.error(String.format("Respuesta del error %d: %s", response.getStatus(), responseJson));
                String str = "Error ejecutando: " + url + ". Se obtuvo el código de error: " + response.getStatus();
                throw new ImageGeneratorApiException(ImageGeneratorApi.class.getName(), str);
            }
            
            log.info("Se ejecutó la query: " + url);
            
            return responseJson;
        } catch (ImageGeneratorApiException e) {
            throw e;
        } catch (Exception e) {
            logException(e);
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
            client.close();
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