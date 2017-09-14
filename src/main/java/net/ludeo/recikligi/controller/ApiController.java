package net.ludeo.recikligi.controller;

import net.ludeo.recikligi.model.UnknownVisualClass;
import net.ludeo.recikligi.model.UnknownVisualClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/api")
public class ApiController {

    private final UnknownVisualClassRepository unknownVisualClassRepository;

    @Autowired
    public ApiController(UnknownVisualClassRepository unknownVisualClassRepository) {
        this.unknownVisualClassRepository = unknownVisualClassRepository;
    }

    @GetMapping("/unknown")
    public @ResponseBody
    Iterable<String> unknownVisualClasses() {
        return StreamSupport.stream(unknownVisualClassRepository.findAll().spliterator(), false)
                .map(UnknownVisualClass::getName)
                .distinct()
                .collect(Collectors.toSet());
    }
}
