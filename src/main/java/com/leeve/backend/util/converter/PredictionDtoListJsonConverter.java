package com.leeve.backend.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leeve.backend.domain.dto.PredictionDto;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Converter
@RequiredArgsConstructor
public class PredictionDtoListJsonConverter implements AttributeConverter<List<PredictionDto>, String> {

  private final ObjectMapper objectMapper;

  @Override
  public String convertToDatabaseColumn(List<PredictionDto> attribute) {
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Error converting List<PredictionDto> to JSON", e);
    }
  }

  @Override
  public List<PredictionDto> convertToEntityAttribute(String dbData) {
    try {
      return objectMapper.readValue(dbData, new TypeReference<>() {});
    } catch (IOException e) {
      throw new IllegalArgumentException("Error converting JSON to List<PredictionDto>", e);
    }
  }
}
