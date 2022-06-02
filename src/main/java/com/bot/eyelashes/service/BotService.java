package com.bot.eyelashes.service;

import com.bot.eyelashes.config.properties.TelegramProperties;
import com.bot.eyelashes.enums.Commands;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.impl.HandeStartImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {

    private final TelegramProperties telegramProperties;
    private final HandeStartImpl startHandler;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Commands commands = Commands.getTypeCommand(update.getMessage().getText());
        String commandName = commands.getCOMMAND();
        Handle command = commands.getHANDLE();
        if(commandName.equals(update.getMessage().getText())){
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
