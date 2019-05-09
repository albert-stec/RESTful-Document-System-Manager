package com.stecalbert.restfuldms.configuration.i18n;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;

@Component
public class LocaleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String locale = Optional
                .ofNullable(request.getHeader("Accept-Language"))
                .orElse("en");
        LocaleContextHolder.setLocale(new Locale(locale));

        return true;
    }
}
