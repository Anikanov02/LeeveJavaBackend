package com.leeve.backend.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leeve.backend.domain.dto.ImageDto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Converter
@RequiredArgsConstructor
public class ImageDtoJsonConverter implements AttributeConverter<ImageDto, String> {

  private final ObjectMapper objectMapper;

  @Override
  public String convertToDatabaseColumn(ImageDto attribute) {
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Error converting ImageDto to JSON", e);
    }
  }

  @Override
  public ImageDto convertToEntityAttribute(String dbData) {
    try {
      return objectMapper.readValue(dbData, ImageDto.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("Error converting JSON to ImageDto", e);
    }
  }
}