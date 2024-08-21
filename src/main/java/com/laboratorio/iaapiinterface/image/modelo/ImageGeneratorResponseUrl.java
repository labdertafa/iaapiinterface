package com.laboratorio.iaapiinterface.image.modelo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 20/08/2024
 * @updated 20/08/2024
 */

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ImageGeneratorResponseUrl {
    private List<ImageGeneratorImageDetails> images;
    private ImageGeneratorTimings timings;
    // private int seed;
    private List<Boolean> has_nsfw_concepts;
    private String prompt;
}