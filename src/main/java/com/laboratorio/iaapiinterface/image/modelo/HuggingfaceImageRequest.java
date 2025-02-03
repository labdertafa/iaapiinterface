package com.laboratorio.iaapiinterface.image.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 02/02/2025
 * @updated 02/12/2025
 */

@Getter @Setter @AllArgsConstructor
public class HuggingfaceImageRequest {
    private String inputs;
    private HuggingfaceParameters parameters;

    public HuggingfaceImageRequest(String inputs) {
        this.inputs = inputs;
        this.parameters = new HuggingfaceParameters(11.5, 50, 384, 512);
    }
}