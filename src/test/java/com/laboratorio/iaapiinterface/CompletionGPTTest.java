package com.laboratorio.iaapiinterface;

import com.laboratorio.iaapiinterface.text.CompletionGPT;
import com.laboratorio.iaapiinterface.text.modelo.ParametrosGPT;
import com.laboratorio.iaapiinterface.text.modelo.ResponseGPT;
import com.laboratorio.iaapiinterface.utils.IAApiInterfaceConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 06/08/2024
 * @updated 06/04/2025
 */
public class CompletionGPTTest {
    @Test
    public void generarPost() throws Exception {
        String prompt = "Dime algo gracioso";
        IAApiInterfaceConfig config = IAApiInterfaceConfig.getInstance();
        String token = config.getProperty("bearer_token");
        String endpoint = config.getProperty("text_endpoint");
        int oKResponse = Integer.valueOf(config.getProperty("ok_response"));
        String role = config.getProperty("text_role");
        String model = config.getProperty("text_model");
        int maxTokens = Integer.parseInt(config.getProperty("text_max_tokens"));
        double temperature = Double.parseDouble(config.getProperty("text_temperature"));
       
        ParametrosGPT param = new ParametrosGPT(token, endpoint, oKResponse, role, model, maxTokens, temperature);
        CompletionGPT completionGPT = new CompletionGPT();
        ResponseGPT response;
        
        response = completionGPT.generarPost(prompt, param);
        
        assertTrue(response != null);
        assertTrue(response.getChoices().size() == 1);
    }
}