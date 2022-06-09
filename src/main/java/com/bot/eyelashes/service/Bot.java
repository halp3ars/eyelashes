package com.bot.eyelashes.service;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.config.properties.TelegramProperties;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.map.CallBackQueryTypeMap;
import com.bot.eyelashes.enums.map.CommandMap;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.callbackquery.Callback;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final TelegramProperties telegramProperties;
    private final BotStateContext botStateContext;
    private SendMessage replyMessage;

    private final CallBackQueryTypeMap callBackQueryTypeMap;
    private final MasterDataCache masterDataCache;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        CommandMap commandMap = new CommandMap();
        BotState botState;
        if (update.hasCallbackQuery()) {
            Callback callback = callBackQueryTypeMap.getCallback(update.getCallbackQuery().getData().split("/")[0]);
            execute(callback.getCallbackQuery(update.getCallbackQuery()));
        } else if (update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("registration")) {
                botState = BotState.FILLING_PROFILE;
                masterDataCache.setUsersCurrentBotState(update.getMessage().getFrom().getId(), botState);
                replyMessage = botStateContext.processInputMessage(botState, update.getMessage());
                execute(replyMessage);
            } else {
                botState = masterDataCache.getUsersCurrentBotState(update.getMessage().getFrom().getId());
                replyMessage = botStateContext.processInputMessage(botState, update.getMessage());
                execute(replyMessage);
            }
            if (update.getMessage().getText().startsWith("/")) {
                Handle handle = commandMap.getCommand(message.getText());
                execute(handle.getMessage(update));
            }
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
