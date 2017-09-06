package net.ludeo.recikligi.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
        String errorStracktrace = getStacktrace(throwable);

        model.addAttribute("error", errorMessage);
        model.addAttribute("status", HttpURLConnection.HTTP_INTERNAL_ERROR);
        model.addAttribute("stacktrace", errorStracktrace);

        return "error";
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
