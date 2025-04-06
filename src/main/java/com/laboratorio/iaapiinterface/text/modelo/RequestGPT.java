package com.laboratorio.iaapiinterface.text.modelo;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Rafael
 * @version 1.2
 * @created 13/05/2023
 * @updated 06/04/2025
 */
@Getter @Setter @AllArgsConstructor
public class RequestGPT {
    private String model;
    private List<MessageGPT> messages;
    private int max_tokens;
    private double temperature;

    public RequestGPT(String model, MessageGPT message, int max_tokens, double temperature) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(message);
        this.max_tokens = max_tokens;
        this.temperature = temperature;
    }
}