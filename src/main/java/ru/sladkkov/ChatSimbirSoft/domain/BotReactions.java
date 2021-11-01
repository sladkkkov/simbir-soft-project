package ru.sladkkov.ChatSimbirSoft.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BotReactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reactionId;

    private String outputMessage;
}
