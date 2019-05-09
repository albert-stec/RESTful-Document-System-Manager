package com.stecalbert.restfuldms.configuration.i18n;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public final class Translator {

    private static ResourceBundleMessageSource messageSource;

    @Autowired
    public Translator(ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    static String toLocale(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(msgCode, ArrayUtils.EMPTY_OBJECT_ARRAY, locale);

        return message;
    }
}
