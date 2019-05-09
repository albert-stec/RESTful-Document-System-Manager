package com.stecalbert.restfuldms.service.impl;

import com.stecalbert.restfuldms.configuration.i18n.Translator;
import com.stecalbert.restfuldms.model.constants.DocumentStatus;
import com.stecalbert.restfuldms.model.dto.DocumentDto;
import com.stecalbert.restfuldms.model.entity.DocumentEntity;
import com.stecalbert.restfuldms.model.entity.UserEntity;
import com.stecalbert.restfuldms.repository.DocumentRepository;
import com.stecalbert.restfuldms.service.DocumentService;
import com.stecalbert.restfuldms.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository,
                               UserService userService,
                               ModelMapper modelMapper) {
        this.documentRepository = documentRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DocumentDto> findAll() {
        List<DocumentEntity> documentEntityList = documentRepository.findAll();
        Type documentDtoListType =
                new TypeToken<List<DocumentDto>>() {
                }.getType();

        return modelMapper.map(documentEntityList, documentDtoListType);
    }

    @Transactional(readOnly = true)
    @Override
    public DocumentDto findById(Long id) {
        Optional<DocumentEntity> optionalDocument = documentRepository.findById(id);
        DocumentEntity documentEntity =
                optionalDocument.orElseThrow(() -> {
                    String message = Translator.toLocale("documentNotFoundExceptionMessage", id);
                    return new EntityNotFoundException(message);
                });

        return modelMapper.map(documentEntity, DocumentDto.class);
    }

    @Transactional
    @Override
    public DocumentDto create(DocumentDto documentDto) {
        UserEntity ownerEntity = userService.getAuthenticatedUser();

        DocumentEntity documentEntity = modelMapper.map(documentDto, DocumentEntity.class);
        documentEntity.setCreationDateTime(LocalDateTime.now());
        documentEntity.setVersion(1);
        documentEntity.setStatus(DocumentStatus.PENDING);
        documentEntity.setOwner(ownerEntity);
        //  todo      documentEntity.setAcceptor();

        DocumentEntity created = documentRepository.save(documentEntity);

        return modelMapper.map(created, DocumentDto.class);
    }


}
