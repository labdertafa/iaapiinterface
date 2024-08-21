package com.laboratorio.iaapiinterface.text.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 13/05/2023
 * @updated 06/08/2024
 */
@Getter @Setter @AllArgsConstructor
public class UsageGPT {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
}