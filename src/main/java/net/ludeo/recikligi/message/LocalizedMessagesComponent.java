package net.ludeo.recikligi.message;

import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;

@Setter
public class LocalizedMessagesComponent implements MessageSourceAware {

    private MessageSource messageSource;

    protected String getMessage(final String code, final Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
