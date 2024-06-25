package com.leeve.backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PredictionDto {
  @JsonProperty("x")
  private Double x;
  @JsonProperty("y")
  private Double y;
  private Double width;
  private Double height;
  private Double confidence;
  @JsonProperty("class")
  private String objectClass;
  @JsonProperty("class_id")
  private Long classId;
  @JsonProperty("detection_id")
  private String detectionId;
}
