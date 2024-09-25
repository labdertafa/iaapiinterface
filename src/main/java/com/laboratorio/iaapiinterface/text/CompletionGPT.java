package com.laboratorio.iaapiinterface.text;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laboratorio.iaapiinterface.text.exception.GPTApiException;
import com.laboratorio.iaapiinterface.text.modelo.MessageGPT;
import com.laboratorio.iaapiinterface.text.modelo.ParametrosGPT;
import com.laboratorio.iaapiinterface.text.modelo.RequestGPT;
import com.laboratorio.iaapiinterface.text.modelo.ResponseGPT;
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
 * @version 2.0
 * @created 13/05/2023
 * @updated 25/09/2024
 */
public class CompletionGPT {
    protected static final Logger log = LogManager.getLogger(CompletionGPT.class);

    private void logException(Exception e) {
        log.error("Error: " + e.getMessage());
        if (e.getCause() != null) {
            log.error("Causa: " + e.getCause().getMessage());
        }
    }

    public ResponseGPT generarPost(String prompt, ParametrosGPT param) throws Exception {
        Client client = ClientBuilder.newClient();
        Response response = null;
        
        log.debug("Parámetros recibidos: " + param);        
        
        try {
            // Se prepara la Request
            MessageGPT message = new MessageGPT(param.getRole(), prompt);
            RequestGPT request = new RequestGPT(param.getModel(), message, param.getMaxTokens(), param.getTemperature(), param.getRepetitionPenalty());
            
            Gson gson = new Gson();
            String requestJson = gson.toJson(request);
            log.debug("Request a enviar a GPT: " + requestJson);
            
            String url = param.getEndpoint();
            WebTarget target = client.target(url);
            
            response = target.request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + param.getBearerToken())
                    .post(Entity.entity(requestJson, MediaType.APPLICATION_JSON));
            
            String responseJson = response.readEntity(String.class);
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                log.error(String.format("Respuesta del error %d: %s", response.getStatus(), responseJson));
                String str = "Error ejecutando: " + url + ". Se obtuvo el código de error: " + response.getStatus();
                throw new GPTApiException(CompletionGPT.class.getName(), str);
            }
            
            log.debug("Se ejecutó la query: " + url);
            log.debug("Respuesta de GPT: " + responseJson);
            
            return gson.fromJson(responseJson, ResponseGPT.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        } catch (GPTApiException e) {
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
            client.close();
        }
    }
}