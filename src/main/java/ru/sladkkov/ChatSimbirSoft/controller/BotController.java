package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sladkkov.ChatSimbirSoft.exception.*;
import ru.sladkkov.ChatSimbirSoft.service.BotService;

import java.io.IOException;
import java.util.List;

@Controller
public class BotController {

    private final BotService botService;

    public BotController(BotService botService) {
        this.botService = botService;
    }


    @GetMapping("/bot")
    public Object botCommand(@RequestBody String command) throws Exception {
        List<String> list = botService.parserCommand(command);
/*
        String strings = botService.youtubeId(command);
*/
        return botService.commandCreate(list);
    }
}
