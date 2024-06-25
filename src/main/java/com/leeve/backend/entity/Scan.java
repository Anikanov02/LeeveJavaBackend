package com.leeve.backend.entity;

import com.leeve.backend.domain.dto.ImageDto;
import com.leeve.backend.domain.dto.PredictionDto;
import com.leeve.backend.util.converter.ImageDtoJsonConverter;
import com.leeve.backend.util.converter.PredictionDtoListJsonConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.TypeDef;
import jakarta.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scan_data")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Scan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double time;

  private Boolean isManuallyChecked;

  @Convert(converter = ImageDtoJsonConverter.class)
  @Column(columnDefinition = "jsonb")
  @ColumnTransformer(write = "?::jsonb")
  private ImageDto image;

  @Convert(converter = PredictionDtoListJsonConverter.class)
  @Column(columnDefinition = "jsonb")
  @ColumnTransformer(write = "?::jsonb")
  private List<PredictionDto> predictions;
}
