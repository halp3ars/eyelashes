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
import com.bot.eyelashes.repository.MasterRepository;
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
    private SendMessage replyMessage;
    private final ClientDataCache clientDataCache;
    private final CallBackQueryTypeMap callBackQueryTypeMap;
    private final MasterDataCache masterDataCache;
    private final ClientBotStateContext clientBotStateContext;
    private final BotStateContext botStateContext;
    private final MasterRepository masterRepository;
    private boolean masterRegistration;
    public static boolean clientRegistration;


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        CommandMap commandMap = new CommandMap();
        BotState botState;
        ClientBotState clientBotState;
        if (update.hasCallbackQuery()) {
            //TODO : вынести callback Master is Bot.java в отдельный класс
            if (update.getCallbackQuery()
                    .getData()
                    .equals("MASTER")) {
                if (masterRepository.existsByTelegramId(update.getCallbackQuery()
                        .getMessage()
                        .getChatId())) {
                    execute(SendMessage.builder()
                            .chatId(update.getCallbackQuery()
                                    .getMessage()
                                    .getChatId()
                                    .toString())
                            .text("Вы авторизированы")
                            .build());
                } else {
                    botState = BotState.ASK_FULL_NAME;
                    masterRegistration = true;
                    masterDataCache.setUsersCurrentBotState(update.getCallbackQuery()
                            .getMessage()
                            .getChatId(), botState);
                    replyMessage = botStateContext.processInputMessage(botState, update);
                    execute(replyMessage);
                }
            } else {
                Callback callback = callBackQueryTypeMap.getCallback(update.getCallbackQuery()
                        .getData()
                        .split("/")[0]);
                execute(callback.getCallbackQuery(update.getCallbackQuery()));
            }
        } else if (update.getMessage()
                .hasText()) {
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
