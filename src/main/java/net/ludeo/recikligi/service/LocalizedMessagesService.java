package net.ludeo.recikligi.service;

import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Setter
@Service
public final class LocalizedMessagesService implements MessageSourceAware {

    private MessageSource messageSource;

    public String getMessage(final String code, final Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
