package com.stecalbert.restfuldms.controller;

import com.stecalbert.restfuldms.model.dto.DocumentDto;
import com.stecalbert.restfuldms.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    private final HttpServletRequest httpServletRequest;

    @Autowired
    public DocumentController(DocumentService documentService, HttpServletRequest httpServletRequest) {
        this.documentService = documentService;
        this.httpServletRequest = httpServletRequest;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<DocumentDto> executeSampleService(
            @RequestPart("documentDto") DocumentDto documentDto,
            @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file) throws IOException {

        documentDto.setFile(file.getBytes());
        DocumentDto created = documentService.create(documentDto);

        return ResponseEntity
                .created(URI.create(httpServletRequest.getRequestURI() + '/' + created.getId()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> findById(@PathVariable Long id) {

        return ResponseEntity
                .ok()
                .body(documentService.findById(id));
    }

}
