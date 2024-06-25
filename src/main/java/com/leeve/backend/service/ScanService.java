package com.leeve.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leeve.backend.config.ApplicationProperties;
import com.leeve.backend.domain.dto.ScanDto;
import com.leeve.backend.repository.ScanRepository;
import com.leeve.backend.util.mapper.ScanMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScanService {
  private final ScanRepository scanRepository;
  private final ObjectMapper objectMapper;
  private final ApplicationProperties properties;

  public List<ScanDto> getAllScans() {
    return scanRepository.findAll().stream()
        .map(ScanMapper.INSTANCE::scanToScanDto)
        .toList();
  }

  public void saveScan(ScanDto dto) {
    scanRepository.save(ScanMapper.INSTANCE.scanDtoToScan(dto));
  }

  public File generateExcelFile(List<ScanDto> scans) throws IOException {
    final File excelFile = new File(properties.getLogsFilePath());
    if (!excelFile.exists()) {
      excelFile.createNewFile();
    }

    try (Workbook workbook = new XSSFWorkbook()) {
      final Sheet sheet = workbook.createSheet("Scan Data");

      final Row headerRow = sheet.createRow(0);
      headerRow.createCell(0).setCellValue("Time");
      headerRow.createCell(1).setCellValue("Is Manually Checked");
      headerRow.createCell(2).setCellValue("Image Width");
      headerRow.createCell(3).setCellValue("Image Height");
      headerRow.createCell(4).setCellValue("Predictions (JSON)");

      int rowNum = 1;
      for (ScanDto scan : scans) {
        final Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(scan.getTime());
        row.createCell(1).setCellValue(scan.isManuallyChecked());
        row.createCell(2).setCellValue(scan.getImage().getWidth());
        row.createCell(3).setCellValue(scan.getImage().getHeight());

        try {
          final String predictionsJson = objectMapper.writeValueAsString(scan.getPredictions());
          row.createCell(4).setCellValue(predictionsJson);
        } catch (JsonProcessingException e) {
          row.createCell(4).setCellValue("Error: Unable to serialize predictions to JSON");
        }
      }

      try (FileOutputStream outputStream = new FileOutputStream(excelFile)) {
        workbook.write(outputStream);
      }
    }

    return excelFile;
  }
}
