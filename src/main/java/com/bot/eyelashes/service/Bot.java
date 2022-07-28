package com.bot.eyelashes.service;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.config.properties.TelegramProperties;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.enums.map.CallBackQueryTypeMap;
import com.bot.eyelashes.enums.map.CommandMap;
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.handler.ClientBotStateContext;
import com.bot.eyelashes.handler.Handle;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.callbackquery.DayCallback;
import com.bot.eyelashes.schedule.controller.ScheduleController;
import com.bot.eyelashes.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private final TelegramProperties telegramProperties;
    private SendMessage replyMessage;
    private final ClientDataCache clientDataCache;
    private final CallBackQueryTypeMap callBackQueryTypeMap;
    private final MasterDataCache masterDataCache;
    private final ClientBotStateContext clientBotStateContext;
    private final BotStateContext botStateContext;
    public static boolean masterRegistration;
    public static boolean clientRegistration;
    private final CommandMap commandMap;
    private final DayCallback dayCallback;
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        BotState botState;
        ClientBotState clientBotState;
        if (update.hasCallbackQuery()) {
            log.info("callback = " + update.getCallbackQuery().getData() + " pressed by " +
                    update.getCallbackQuery().getFrom().getUserName());
            if (update.getCallbackQuery().getData().startsWith("MASTER_DAY")) {
                execute(dayCallback.processInputMessage(update));
            } else {
                ScheduleService.masterId = update.getCallbackQuery().getMessage().getChatId();
                Callback callback = callBackQueryTypeMap.getCallback(update.getCallbackQuery().getData()
                        .split("/")[0]);
                execute(callback.getCallbackQuery(update.getCallbackQuery()));
            }
        } else if (update.getMessage().hasText()) {
            ScheduleService.masterId = update.getMessage().getChatId();
            if ((update.getMessage().getText().startsWith("/"))) {
                Handle handle = commandMap.getCommand(message.getText());
                execute(handle.getMessage(update));
            }
        }
        if (masterRegistration) {
            if (update.hasMessage()) {
                botState = masterDataCache.getUsersCurrentBotState(update.getMessage().getChatId());
                replyMessage = botStateContext.processInputMessage(botState, message);
                execute(replyMessage);
            }
        } else if (clientRegistration) {
            if (update.hasMessage()) {
                clientBotState = clientDataCache.getClientBotState(update.getMessage().getChatId());
                replyMessage = clientBotStateContext.processInputClientMessage(clientBotState, update.getMessage());
                execute(replyMessage);
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
