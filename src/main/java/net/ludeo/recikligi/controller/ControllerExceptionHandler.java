package net.ludeo.recikligi.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.util.HtmlUtils;

import java.net.HttpURLConnection;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MissingServletRequestPartException.class)
    public String handleMissingParams() {
        return "redirect:/camera";
    }

    @ExceptionHandler(Throwable.class)
    public String handleException(final Throwable throwable, final Model model) {
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("error", HtmlUtils.htmlEscape(errorMessage));
        model.addAttribute("status", HttpURLConnection.HTTP_INTERNAL_ERROR);
        return "error";
    }
}
