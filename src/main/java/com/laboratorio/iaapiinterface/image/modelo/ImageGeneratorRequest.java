package com.laboratorio.iaapiinterface.image.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 19/08/2024
 * @updated 01/09/2025
 */

@Getter @Setter @AllArgsConstructor
public class ImageGeneratorRequest {
    private Integer seed;
    private String prompt;
    private int width;
    private int height;
    private double guidance_scale;
    private int num_inference_steps;

    public ImageGeneratorRequest(String prompt, int width, int height, double guidance_scale, int num_inference_steps) {
        this.seed = null;
        this.prompt = prompt;
        this.width = width;
        this.height = height;
        this.guidance_scale = guidance_scale;
        this.num_inference_steps = num_inference_steps;
    }
}