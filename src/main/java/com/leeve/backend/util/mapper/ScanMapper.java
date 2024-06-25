package com.leeve.backend.util.mapper;

import com.leeve.backend.domain.dto.ScanDto;
import com.leeve.backend.entity.Scan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScanMapper {

  ScanMapper INSTANCE = Mappers.getMapper(ScanMapper.class);

  @Mapping(target = "id", ignore = true)
  Scan scanDtoToScan(ScanDto scanDto);

  ScanDto scanToScanDto(Scan scan);
}