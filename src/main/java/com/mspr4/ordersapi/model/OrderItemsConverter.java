package com.mspr4.ordersapi.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGobject;

import java.util.List;

@Converter
public class OrderItemsConverter implements AttributeConverter<List<OrderItem>, PGobject> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PGobject convertToDatabaseColumn(List<OrderItem> attribute) {
        try {
            PGobject pgObject = new PGobject();
            pgObject.setType("jsonb");
            pgObject.setValue(objectMapper.writeValueAsString(attribute)); // sérialisation JSON
            return pgObject;
        } catch (Exception e) {
            throw new IllegalArgumentException("Erreur de sérialisation JSON", e);
        }
    }

    @Override
    public List<OrderItem> convertToEntityAttribute(PGobject dbData) {
        try {
            return objectMapper.readValue(dbData.getValue(), new TypeReference<List<OrderItem>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Erreur de désérialisation JSON", e);
        }
    }
}