package com.leeve.backend.controller;

import com.leeve.backend.domain.dto.ScanDto;
import com.leeve.backend.service.ScanService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/v1/scan")
@RequiredArgsConstructor
public class ScanController {
  private final ScanService scanService;

  @PostMapping//(consumes = "multipart/form-data")
  public ResponseEntity<?> saveScans(@RequestBody ScanDto scanData
      //, @RequestPart("file") MultipartFile file
  ) {
    scanService.saveScan(scanData);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<ScanDto>> getAllScans() {
    return ResponseEntity.ok(scanService.getAllScans());
  }

  @GetMapping("/xlsx")
  public ResponseEntity<?> getLogs() {
    final List<ScanDto> scans = scanService.getAllScans();
    try {
      final File file = scanService.generateExcelFile(scans);

      // Load file as a resource
      final Path path = file.toPath();
      final ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

      final HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

      return ResponseEntity.ok()
          .headers(headers)
          .contentLength(file.length())
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(resource);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
