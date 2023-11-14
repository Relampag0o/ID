package org.example.apimodels;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Datum{
    public String id;
    public String type;
    public Links links;
    public Attributes attributes;
    public Relationships relationships;

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al convertir a JSON";
        }
    }
}
