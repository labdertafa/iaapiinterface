package com.laboratorio.iaapiinterface.image.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 19/08/2024
 * @updated 19/08/2024
 */

@Getter @Setter @AllArgsConstructor
public class ImageGeneratorRequest {
    private String prompt;
    private String model;
    private int steps;
    private int n;
    private String size;

    public ImageGeneratorRequest(String prompt, String model, int steps, String size) {
        this.prompt = prompt;
        this.model = model;
        this.steps = steps;
        this.n = 1;
        this.size = size;
    }
}