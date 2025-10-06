package com.laboratorio.iaapiinterface.image.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 19/08/2024
 * @updated 06/10/2025
 */

@Getter @Setter @AllArgsConstructor
public class ImageGeneratorRequest {
    private String model;
    private String prompt;
    private int width;
    private int height;
    private double guidance_scale;
    private int num_inference_steps;
}