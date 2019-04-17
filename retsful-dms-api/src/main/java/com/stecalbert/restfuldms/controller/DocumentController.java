package com.stecalbert.restfuldms.controller;

import com.stecalbert.restfuldms.model.dto.DocumentDto;
import com.stecalbert.restfuldms.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @PostMapping
    public ResponseEntity<DocumentDto> create(@RequestBody @Valid DocumentDto documentDto) {
        DocumentDto created = documentService.create(documentDto);

        return ResponseEntity
                .created(URI.create(httpServletRequest.getRequestURI() + '/' + created.getId()))
                .body(created);
    }

}
