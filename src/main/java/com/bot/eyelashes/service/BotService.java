package com.bot.eyelashes.service;

import com.bot.eyelashes.config.properties.TelegramProperties;
import com.bot.eyelashes.enums.CallbackQueryType;
import com.bot.eyelashes.enums.Commands;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.impl.HandleStartImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {

    private final TelegramProperties telegramProperties;
    private final HandleStartImpl startHandler;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQueryType callbackQueryType = CallbackQueryType.getTypeCommand(update.getCallbackQuery()
                    .getData());
            execute(callbackQueryType.getCALLBACK()
                    .getCallbackQuery(update.getCallbackQuery()));
        } else if (update.getMessage()
                .hasText()) {
            Commands commands = Commands.getTypeCommand(update.getMessage()
                    .getText());
            String commandName = commands.getCOMMAND();
            Handle command = commands.getHANDLE();
            execute(command.getMessage(update));
        }

    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getNameBot();
    }


    @Override
    public String getBotToken() {
        return telegramProperties.getTokenBot();
    }

}
