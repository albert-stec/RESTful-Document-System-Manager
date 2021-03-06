package com.stecalbert.restfuldms.configuration.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public final class Translator {

    private static ResourceBundleMessageSource messageSource;

    @Autowired
    private Translator(ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    static String toLocale(String msgCode, Object... params) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, params, locale);
    }
}
