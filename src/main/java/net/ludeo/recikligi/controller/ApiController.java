package net.ludeo.recikligi.controller;

import net.ludeo.recikligi.model.UnknownVisualClass;
import net.ludeo.recikligi.model.UnknownVisualClassRepository;
import net.ludeo.recikligi.model.UsageHistoryCount;
import net.ludeo.recikligi.model.UsageHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/api")
public class ApiController {

    private final UnknownVisualClassRepository unknownVisualClassRepository;

    private final UsageHistoryRepository usageHistoryRepository;

    @Autowired
    public ApiController(UnknownVisualClassRepository unknownVisualClassRepository,
            UsageHistoryRepository usageHistoryRepository) {
        this.unknownVisualClassRepository = unknownVisualClassRepository;
        this.usageHistoryRepository = usageHistoryRepository;
    }

    @GetMapping("/unknown")
    public @ResponseBody
    Iterable<String> unknownVisualClasses() {
        return StreamSupport.stream(unknownVisualClassRepository.findAll().spliterator(), false)
                .map(UnknownVisualClass::getName)
                .distinct()
                .collect(Collectors.toSet());
    }

    @GetMapping("/usage")
    public @ResponseBody List<UsageHistoryCount> usageHistoryCount() {
        return usageHistoryRepository.findUsageHistoryCount();
    }
}
