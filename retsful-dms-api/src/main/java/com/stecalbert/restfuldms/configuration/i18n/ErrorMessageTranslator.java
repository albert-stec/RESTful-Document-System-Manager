package com.stecalbert.restfuldms.configuration.i18n;

import com.stecalbert.restfuldms.exception.DocumentNotFoundException;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;

@Component
public class ErrorMessageTranslator extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        final Throwable error = super.getError(webRequest);

        if (Objects.nonNull(error.getMessage())) {
            Object[] params = ArrayUtils.EMPTY_OBJECT_ARRAY;

            if (error instanceof DocumentNotFoundException) {
                params = ((DocumentNotFoundException) error).getParams();
            }

            String localizedMessage = Translator.toLocale(error.getMessage(), params);
            errorAttributes.put("message", localizedMessage);
        }

        return errorAttributes;
    }
}
