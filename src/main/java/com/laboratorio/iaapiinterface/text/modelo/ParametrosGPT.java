package com.laboratorio.iaapiinterface.text.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 09/08/2024
 * @updated 21/08/2024
 */

@Getter @Setter @AllArgsConstructor
public class ParametrosGPT {
    private String bearerToken;
    private String endpoint;
    private String role;
    private String model;
    private int maxTokens;
    private double temperature;
    private double repetitionPenalty;

    @Override
    public String toString() {
        return "ParametrosGPT{" + "bearerToken=" + bearerToken + ", endpoint=" + endpoint + ", role=" + role + ", model=" + model + ", maxTokens=" + maxTokens + ", temperature=" + temperature + ", repetitionPenalty=" + repetitionPenalty + '}';
    }
}