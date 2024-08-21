package com.laboratorio.iaapiinterface.image.modelo;

import java.math.BigDecimal;
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
public class ImageGeneratorTimings {
    private BigDecimal inference; 
}