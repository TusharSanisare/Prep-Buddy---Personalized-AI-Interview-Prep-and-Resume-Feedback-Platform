package com.example.Prep_Buddy_Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.Prep_Buddy_Backend.services.PdfService;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

  @Autowired
  private PdfService pdfService;

  @PostMapping("/extract-text")
  public ResponseEntity<String> extractTextFromPdf(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf") ||
        !"application/pdf".equals(file.getContentType())) {
      return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Invalid file. Please upload a PDF file.");
    }

    try {
      String text = pdfService.extractText(file);
      return ResponseEntity.ok(text);
    } catch (IOException e) {

      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error processing PDF file: " + e.getMessage());
    }
  }
}
