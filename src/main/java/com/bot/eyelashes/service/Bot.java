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
import com.bot.eyelashes.handler.BotStateContext;
import com.bot.eyelashes.handler.BotStateHandleContext;
import com.bot.eyelashes.handler.ClientBotStateContext;
import com.bot.eyelashes.handler.Handle;
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
    private final BotStateContext botStateContext;
    private final ClientBotStateContext clientBotStateContext;
    private final ScheduleStateContext scheduleStateContext;
    private SendMessage replyMessage;
    private final ClientDataCache clientDataCache;
    private final CallBackQueryTypeMap callBackQueryTypeMap;
    private final MasterDataCache masterDataCache;
    private final ScheduleDataCacheImpl scheduleDataCache;
    private final MasterRepository masterRepository;
    private final BotStateHandleContext botStateHandleContext;

    private boolean masterRegistration = true;
    private boolean clientRegistration = true;
    private boolean schedule = true;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        CommandMap commandMap = new CommandMap();
        BotState botState;
        StateSchedule stateSchedule;
        ClientBotState clientBotState;
        if (update.hasCallbackQuery()) {
            Callback callback = callBackQueryTypeMap.getCallback(update.getCallbackQuery().getData().split("/")[0]);
            masterRegistration = false;
            clientRegistration = false;
            schedule = false;
            if (update.getCallbackQuery().getData().equals("REGISTRATION")) {
                botState = BotState.FILLING_PROFILE;
                masterDataCache.setUsersCurrentBotState(Long.valueOf(update.getCallbackQuery().getId()), botState);
                replyMessage = botStateContext.processCallback(botState, update.getCallbackQuery());
                masterRegistration = true;
                execute(replyMessage);
            }
            execute(callback.getCallbackQuery(update.getCallbackQuery()));
        } else if (update.getMessage().hasText()) {
            if (update.getMessage()
                    .getText()
                    .equals("clientRegistration")) {
                clientBotState = ClientBotState.FILLING_CLIENT_PROFILE;
                masterRegistration = false;
                clientDataCache.setClientBotState(update.getMessage()
                        .getFrom()
                        .getId(), clientBotState);
                replyMessage = clientBotStateContext.processInputClientMessage(clientBotState, update.getMessage());
                execute(replyMessage);
            } else if (update.getMessage()
                    .getText()
                    .equals("Расписание")) {
                stateSchedule = StateSchedule.FILLING_SCHEDULE;
                scheduleDataCache.setUsersCurrentBotState(update.getMessage()
                        .getFrom()
                        .getId(), stateSchedule);
                replyMessage = scheduleStateContext.processInputMessage(stateSchedule, update.getMessage());
                schedule = true;
                masterRegistration = false;
                clientRegistration = false;
                execute(replyMessage);
            } else if (update.getMessage().getText().equals("Авторизация".toLowerCase())) {
                if (masterRepository.existsByTelegramId(update.getMessage().getFrom().getId())) {
                    masterRegistration = false;
                    clientRegistration = false;
                    schedule = false;
                    execute(messageForAuthMaster(update.getMessage().getChatId()));
                }
            } else if ((update.getMessage().getText().startsWith("/"))) {
                Handle handle = commandMap.getCommand(message.getText());
                execute(handle.getMessage(update));
            }else {
                if (masterRegistration) {
                    botState = masterDataCache.getMessageCurrentState(update.getMessage()
                            .getFrom()
                            .getId());
                    replyMessage = botStateHandleContext.processInputMessage(botState, update.getMessage());
                    execute(replyMessage);
                }  if (clientRegistration) {
                    clientBotState = clientDataCache.getClientBotState(update.getMessage()
                            .getFrom()
                            .getId());
                    replyMessage = clientBotStateContext.processInputClientMessage(clientBotState, update.getMessage());
                    execute(replyMessage);
                } else if (schedule) {
                    stateSchedule = scheduleDataCache.getUsersCurrentBotState(update.getMessage()
                            .getFrom()
                            .getId());
                    replyMessage = scheduleStateContext.processInputMessage(stateSchedule, update.getMessage());
                    execute(replyMessage);
                }
            }
        }
    }

    private SendMessage messageForAuthMaster(Long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Arrays.asList(
                InlineKeyboardButton.builder()
                        .text("Список клиентов")
                        .callbackData("LIST_CLIENT")
                        .build()
        ));
        inlineKeyboardMarkup.setKeyboard(buttons);
        return SendMessage.builder()
                .text("Для авторизированного мастера")
                .replyMarkup(inlineKeyboardMarkup)
                .chatId(chatId.toString())
                .build();
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
