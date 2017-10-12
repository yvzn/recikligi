package net.ludeo.recikligi.controller;

import net.ludeo.recikligi.service.LocalizedMessagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private final LocalizedMessagesService localizedMessagesService;

    @Autowired
    public ControllerExceptionHandler(LocalizedMessagesService localizedMessagesService) {
        this.localizedMessagesService = localizedMessagesService;
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public String handleMissingParams() {
        return "redirect:/camera";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleException(final MaxUploadSizeExceededException ex) {
        String errorMessage = localizedMessagesService.getMessage("error.msg.file.too.large");
        return buildModelAndView(errorMessage, ex);
    }

    @ExceptionHandler(DataAccessException.class)
    public ModelAndView handleException(final DataAccessException ex) {
        String errorMessage = localizedMessagesService.getMessage("error.msg.data.update");
        return buildModelAndView(errorMessage, ex);
    }

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleGenericException(final Throwable throwable) {
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        return buildModelAndView(errorMessage, throwable);
    }

    private ModelAndView buildModelAndView(final String errorMessage, final Throwable throwable) {
        logger.debug(errorMessage, throwable);

        String errorStracktrace = getStacktrace(throwable);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", errorMessage);
        modelAndView.addObject("status", HttpURLConnection.HTTP_INTERNAL_ERROR);
        modelAndView.addObject("stacktrace", errorStracktrace);
        modelAndView.setViewName("error");
        return modelAndView;
    }

    private String getStacktrace(Throwable throwable) {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
