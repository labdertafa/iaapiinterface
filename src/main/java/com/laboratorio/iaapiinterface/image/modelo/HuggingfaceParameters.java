package com.laboratorio.iaapiinterface.image.modelo;

import com.google.gson.annotations.SerializedName;
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
public class HuggingfaceParameters {
    @SerializedName("guidance_scale")
    private double guidanceScale;
    @SerializedName("num_inference_steps")
    private int numInferenceSteps;
    private int height;
    private int width;
}