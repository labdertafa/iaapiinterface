package com.laboratorio.iaapiinterface.text.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.0
 * @created 06/08/2023
 * @updated 06/08/2024
 */
@Getter @Setter @AllArgsConstructor
public class MessageGPT {
    private String role;
    private String content;
}