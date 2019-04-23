package com.stecalbert.restfuldms.service;

import com.stecalbert.restfuldms.model.dto.DocumentDto;

import java.util.List;

public interface DocumentService {

    List<DocumentDto> findAll();

    DocumentDto findById(Long id);

    DocumentDto create(DocumentDto documentDto);
}
