package com.janning_owns_it.tarot.controller;

import com.janning_owns_it.tarot.model.TarotReadingResponse;
import com.janning_owns_it.tarot.service.TarotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tarot")
public class TarotController {

    TarotService tarotService;

    @GetMapping
    public TarotReadingResponse getResult(@RequestParam String querentsQuestion) throws Exception {
        tarotService = new TarotService();
        return tarotService.getReading(querentsQuestion);
    }
}
