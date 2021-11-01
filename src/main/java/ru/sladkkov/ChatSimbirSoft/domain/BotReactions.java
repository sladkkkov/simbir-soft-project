package ru.sladkkov.ChatSimbirSoft.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class BotReactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reactionId;

    private String outputMessage;
}
