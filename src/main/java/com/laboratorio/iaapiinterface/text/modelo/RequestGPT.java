package com.laboratorio.iaapiinterface.text.modelo;

import java.util.ArrayList;
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
public class RequestGPT {
    private String model;
    private List<MessageGPT> messages;
    private int max_tokens;
    private double temperature;
    private double repetition_penalty;
    private boolean stream;

    public RequestGPT(String model, MessageGPT message, int max_tokens, double temperature, double repetition_penalty) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(message);
        this.max_tokens = max_tokens;
        this.temperature = temperature;
        this.repetition_penalty = repetition_penalty;
        this.stream = false;
    }
}