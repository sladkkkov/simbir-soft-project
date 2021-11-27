package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class BotController {

    public ResponseEntity botCommand(String command){

        return ResponseEntity.ok("Команда: " + command + " успешно выполнена.");
    }
}
