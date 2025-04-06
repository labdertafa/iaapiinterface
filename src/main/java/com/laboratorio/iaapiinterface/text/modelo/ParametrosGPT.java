package com.laboratorio.iaapiinterface.text.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 09/08/2024
 * @updated 06/48/2025
 */

@Getter @Setter @AllArgsConstructor
public class ParametrosGPT {
    private String bearerToken;
    private String endpoint;
    private int okResponse;
    private String role;
    private String model;
    private int maxTokens;
    private double temperature;

    @Override
    public String toString() {
        return "ParametrosGPT{" + "bearerToken=" + bearerToken + ", endpoint=" + endpoint + ", role=" + role + ", model=" + model + ", maxTokens=" + maxTokens + ", temperature=" + temperature + '}';
    }
}