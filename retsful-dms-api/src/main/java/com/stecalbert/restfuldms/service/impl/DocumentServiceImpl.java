package com.stecalbert.restfuldms.service.impl;

import com.googlecode.jmapper.JMapper;
import com.stecalbert.restfuldms.model.constants.DocumentStatus;
import com.stecalbert.restfuldms.model.dto.DocumentDto;
import com.stecalbert.restfuldms.model.entity.DocumentEntity;
import com.stecalbert.restfuldms.model.entity.UserEntity;
import com.stecalbert.restfuldms.repository.DocumentRepository;
import com.stecalbert.restfuldms.service.DocumentService;
import com.stecalbert.restfuldms.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final UserService userService;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, UserService userService) {
        this.documentRepository = documentRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DocumentDto> findAll() {
        List<DocumentEntity> documentEntityList = documentRepository.findAll();

        return new ModelMapper()
                .map(documentEntityList, documentEntityList.getClass());
    }

    @Transactional
    @Override
    public DocumentDto create(DocumentDto documentDto) {
        UserEntity ownerEntity = userService.getAuthenticatedUser();

        DocumentEntity documentEntity = new ModelMapper().map(documentDto, DocumentEntity.class);
        documentEntity.setCreationDateTime(LocalDateTime.now());
        documentEntity.setVersion(1);
        documentEntity.setStatus(DocumentStatus.PENDING);
        documentEntity.setOwner(ownerEntity);
//  todo      documentEntity.setFile();
//  todo      documentEntity.setAcceptor();

        DocumentEntity created = documentRepository.save(documentEntity);
        JMapper<DocumentDto, DocumentEntity> mapper =
                new JMapper<>(DocumentDto.class, DocumentEntity.class);

        return mapper.getDestination(created);
    }


}
