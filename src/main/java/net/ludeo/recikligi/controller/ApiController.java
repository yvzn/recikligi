package net.ludeo.recikligi.controller;

import net.ludeo.recikligi.model.UnknownVisualClass;
import net.ludeo.recikligi.model.UnkownVisualClassRepository;
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

    private final UnkownVisualClassRepository unkownVisualClassRepository;

    @Autowired
    public ApiController(UnkownVisualClassRepository unkownVisualClassRepository) {
        this.unkownVisualClassRepository = unkownVisualClassRepository;
    }

    @GetMapping("/unknown")
    public @ResponseBody
    Iterable<String> unknownVisualClasses() {
        return StreamSupport.stream(unkownVisualClassRepository.findAll().spliterator(), false)
                .map(UnknownVisualClass::getName)
                .collect(Collectors.toSet());
    }
}
