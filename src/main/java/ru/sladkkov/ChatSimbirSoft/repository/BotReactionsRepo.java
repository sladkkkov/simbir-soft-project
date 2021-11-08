package ru.sladkkov.ChatSimbirSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sladkkov.ChatSimbirSoft.domain.BotReactions;

public interface BotReactionsRepo extends JpaRepository<BotReactions,Long> {
}
