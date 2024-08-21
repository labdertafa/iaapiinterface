package com.laboratorio.iaapiinterface.text.modelo;

import java.util.List;
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
public class ResponseGPT {
    private String id;
    private String object;
    private int created;
    private String model;
    private List<ChoiceGPT> choices;
    private UsageGPT usage;
}