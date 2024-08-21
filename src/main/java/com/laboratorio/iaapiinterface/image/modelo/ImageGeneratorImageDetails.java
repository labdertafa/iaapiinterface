package com.laboratorio.iaapiinterface.image.modelo;

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
public class ImageGeneratorImageDetails {
    private String url;
    private int width;
    private int height;
    private String content_type;
}