package com.laboratorio.iaapiinterface.text;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.laboratorio.clientapilibrary.ApiClient;
import com.laboratorio.clientapilibrary.exceptions.ApiClientException;
import com.laboratorio.clientapilibrary.model.ApiMethodType;
import com.laboratorio.clientapilibrary.model.ApiRequest;
import com.laboratorio.clientapilibrary.model.ApiResponse;
import com.laboratorio.iaapiinterface.text.modelo.MessageGPT;
import com.laboratorio.iaapiinterface.text.modelo.ParametrosGPT;
import com.laboratorio.iaapiinterface.text.modelo.RequestGPT;
import com.laboratorio.iaapiinterface.text.modelo.ResponseGPT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Rafael
 * @version 2.1
 * @created 13/05/2023
 * @updated 18/10/2024
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
        ApiClient client = new ApiClient();
        log.debug("Parámetros recibidos: " + param);        
        
        try {
            // Se prepara la Request
            MessageGPT message = new MessageGPT(param.getRole(), prompt);
            RequestGPT requestGPT = new RequestGPT(param.getModel(), message, param.getMaxTokens(), param.getTemperature(), param.getRepetitionPenalty());
            
            Gson gson = new Gson();
            String requestJson = gson.toJson(requestGPT);
            log.debug("Request a enviar a GPT: " + requestJson);
            
            String url = param.getEndpoint();
            ApiRequest request = new ApiRequest(url, 201, ApiMethodType.POST, requestJson);
            request.addApiHeader("Authorization", "Bearer " + param.getBearerToken());
            
            ApiResponse response = client.executeApiRequest(request);
            
            return gson.fromJson(response.getResponseStr(), ResponseGPT.class);
        } catch (JsonSyntaxException e) {
            logException(e);
            throw e;
        } catch (ApiClientException e) {
            throw e;
        }
    }
}