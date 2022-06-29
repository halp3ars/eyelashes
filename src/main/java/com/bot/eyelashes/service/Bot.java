package com.bot.eyelashes.service;

import com.bot.eyelashes.cache.ClientDataCache;
import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.cache.ScheduleDataCacheImpl;
import com.bot.eyelashes.config.properties.TelegramProperties;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.enums.StateSchedule;
import com.bot.eyelashes.enums.map.CallBackQueryTypeMap;
import com.bot.eyelashes.enums.map.CommandMap;
import com.bot.eyelashes.handler.*;
import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.handler.schedule.ScheduleStateContext;
import com.bot.eyelashes.repository.MasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final TelegramProperties telegramProperties;

    private final ScheduleStateContext scheduleStateContext;
    private SendMessage replyMessage;
    private final ClientDataCache clientDataCache;
    private final CallBackQueryTypeMap callBackQueryTypeMap;
    private final MasterDataCache masterDataCache;
    private final ScheduleDataCacheImpl scheduleDataCache;
    private final ClientBotStateContext clientBotStateContext;

    private final BotStateContext botStateContext;
    private final HandleScheduleContext handleScheduleContext;
    private final MasterRepository masterRepository;

    private boolean masterRegistration;
    private boolean clientRegistration;
    private boolean schedule;


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        CommandMap commandMap = new CommandMap();
        BotState botState;
        StateSchedule stateSchedule;
        ClientBotState clientBotState;
        if (update.hasCallbackQuery()) {
            //TODO : вынести callback Master is Bot.java в отдельный класс
            if (update.getCallbackQuery().getData().equals("MASTER")) {
                if (masterRepository.existsByTelegramId(update.getCallbackQuery().getMessage().getChatId())) {
                    execute(SendMessage.builder()
                            .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                            .text("Вы авторизированы")
                            .build());
                }else {
                    botState = BotState.ASK_FULL_NAME;
                    masterRegistration = true;
                    schedule = false;
                    masterDataCache.setUsersCurrentBotState(update.getCallbackQuery()
                            .getMessage()
                            .getChatId(), botState);
                    replyMessage = botStateContext.processInputMessage(botState, update);
                    execute(replyMessage);
                }

            } else if (update.getCallbackQuery()
                    .getData()
                    .equals("RECORD")) {
                clientBotState = ClientBotState.ASK_CLIENT_FULL_NAME;
                clientRegistration = true;
                clientDataCache.setClientBotState(update.getCallbackQuery()
                        .getMessage()
                        .getChatId(), clientBotState);
                replyMessage = clientBotStateContext.processInputClientMessage(clientBotState, update);
                execute(replyMessage);
            } else if (update.getCallbackQuery()
                    .getData()
                    .equals("SCHEDULE")) {
                stateSchedule = StateSchedule.ASK_DATA_TIME;
                scheduleDataCache.setUsersCurrentBotState(update.getCallbackQuery()
                        .getMessage()
                        .getChatId(), stateSchedule);
                replyMessage = scheduleStateContext.processCallback(stateSchedule, update.getCallbackQuery());
                schedule = true;
                masterRegistration = false;
                execute(replyMessage);
            } else {
                Callback callback = callBackQueryTypeMap.getCallback(update.getCallbackQuery()
                        .getData()
                        .split("/")[0]);
                execute(callback.getCallbackQuery(update.getCallbackQuery()));
            }

        } else if (update.getMessage().hasText()) {
            if ((update.getMessage()
                    .getText()
                    .startsWith("/"))) {
                Handle handle = commandMap.getCommand(message.getText());
                execute(handle.getMessage(update));
            }
            if (masterRegistration) {
                botState = masterDataCache.getUsersCurrentBotState(update.getMessage()
                        .getFrom()
                        .getId());
                replyMessage = botStateContext.processInputMessage(botState, update);
                execute(replyMessage);
            } else if (clientRegistration) {
                clientBotState = clientDataCache.getClientBotState(update.getMessage()
                        .getFrom()
                        .getId());
                replyMessage = clientBotStateContext.processInputClientMessage(clientBotState, update);
                execute(replyMessage);
            } else if (schedule) {
                stateSchedule = scheduleDataCache.getMessageCurrentState(update.getMessage()
                        .getFrom()
                        .getId());
                replyMessage = handleScheduleContext.processInputMessage(stateSchedule, update.getMessage());
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
