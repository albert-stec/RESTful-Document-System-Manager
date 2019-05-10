package com.stecalbert.restfuldms.configuration.i18n;

import com.stecalbert.restfuldms.exception.WithParamsException;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Optional;

@Component
public class ErrorMessageTranslator extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        final Throwable error = super.getError(webRequest);
        Optional
                .ofNullable(error.getMessage())
                .ifPresent(e -> messageToLocale(error, errorAttributes));

        return errorAttributes;
    }

    private static void messageToLocale(Throwable error, Map<String, Object> errorAttributes) {
        Object[] params = getParamsIfPresent(error);
        String localizedMessage = Translator.toLocale(error.getMessage(), params);
        errorAttributes.put("message", localizedMessage);
    }

    private static Object[] getParamsIfPresent(Throwable error) {
        return error instanceof WithParamsException
                ? ((WithParamsException) error).getParams()
                : ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
}
