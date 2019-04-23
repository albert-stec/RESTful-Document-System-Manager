package com.stecalbert.restfuldms.configuration.mapper;

import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import com.stecalbert.restfuldms.model.dto.DocumentDto;
import com.stecalbert.restfuldms.model.entity.DocumentEntity;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Base64;

//@Component
//public class DocumentDtoMapper
//        extends PropertyMapConfigurerSupport<DocumentEntity, DocumentDto> {
//
//    @Override
//    public PropertyMap<DocumentEntity, DocumentDto> mapping() {
//
//        return new PropertyMap<>() {
//            @Override
//            protected void configure() {
//                byte[] file = source.getFile();
//                String base64File =
//                        Base64.getMimeEncoder().encodeToString(file);
//                map().setBase64File(base64File);
//            }
//        };
//    }
//}

@Component
public class DocumentDtoMapper extends ConverterConfigurerSupport<DocumentEntity, DocumentDto> {

    @Override
    protected Converter<DocumentEntity, DocumentDto> converter() {
        return new AbstractConverter<>() {

            @Override
            protected DocumentDto convert(DocumentEntity source) {

                // At first map with default behaviour (fields with the same name and type)
                DocumentDto documentDto =
                        new ModelMapper().map(source, DocumentDto.class);

                // Extend default mapping

                // with conversion from byte[] to base64 encoded string
                byte[] file = source.getFile();
                String base64File =
                        Base64.getMimeEncoder().encodeToString(file);

                documentDto.setBase64File(base64File);

                return documentDto;

            }
        };
    }
}
