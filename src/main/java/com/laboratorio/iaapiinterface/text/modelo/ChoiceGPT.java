package com.laboratorio.iaapiinterface.text.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.1
 * @created 13/05/2023
 * @updated 06/08/2023
 */
@Getter @Setter @AllArgsConstructor
public class ChoiceGPT {
    private int index;
    private MessageGPT message;
    private int logprobs;
    private String finish_reason;

}