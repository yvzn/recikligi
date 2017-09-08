package net.ludeo.recikligi.controller;

import net.ludeo.recikligi.message.LocalizedMessagesComponent;
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
public class ControllerExceptionHandler extends LocalizedMessagesComponent {

    @ExceptionHandler(MissingServletRequestPartException.class)
    public String handleMissingParams() {
        return "redirect:/camera";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleException(final MaxUploadSizeExceededException ex) {
        String errorMessage = getMessage("error.msg.file.too.large");
        return buildModelAndView(errorMessage, ex);
    }

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleGenericException(final Throwable throwable) {
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        return buildModelAndView(errorMessage, throwable);
    }

    private ModelAndView buildModelAndView(final String errorMessage, final Throwable throwable) {
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
