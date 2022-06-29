package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.handler.callbackquery.CallbackServiceImpl;
import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
@RequiredArgsConstructor
public class FillingMasterProfile implements HandleRegistration {
    private final MasterDataCache masterDataCache;
    private final MessageService messageService;
    private final CallbackServiceImpl callbackService;

    @Override
    public SendMessage getMessage(Update update) {
        Message message;

        if(update.hasCallbackQuery()){
            message = update.getCallbackQuery().getMessage();
        }else {
            message = update.getMessage();
        }

        if (masterDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.FILLING_PROFILE)) {
            masterDataCache.setUsersCurrentBotState(message.getFrom()
                    .getId(), BotState.ASK_FULL_NAME);
        }
        return processUsersInput(update);
    }

    @Override
    public BotState getHandleName() {
        return BotState.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Update update) {
        Message message = update.getMessage();
        String masterAnswer;
        Long userId;
        Long chatId;
        if (update.hasCallbackQuery()) {
            masterAnswer = update.getCallbackQuery()
                    .getMessage()
                    .getText();
            chatId = update.getCallbackQuery()
                    .getMessage()
                    .getChatId();
            userId = update.getCallbackQuery()
                    .getMessage()
                    .getChatId();
        } else {
            masterAnswer = message.getText();
            userId = message.getFrom()
                    .getId();
            chatId = message.getChatId();
            log.info(userId + " " + chatId);
        }

        MasterDto masterDto = masterDataCache.getUserProfileData(userId);
        BotState botState = masterDataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;
        if (botState.equals(BotState.ASK_FULL_NAME)) {
            replyToUser = messageService.getReplyMessage(chatId, "Введите ФИО");
            masterDataCache.setUsersCurrentBotState(userId, BotState.ASK_PHONE);
        }

        if (botState.equals(BotState.ASK_PHONE)) {
            String[] fullName = masterAnswer.split(" ");
            masterDto.setSurname(fullName[0]);
            masterDto.setName(fullName[1]);
            masterDto.setMiddleName(fullName[2]);
            replyToUser = messageService.getReplyMessage(chatId, "Введите номер телефона начиная с +7(): ");
            masterDataCache.setUsersCurrentBotState(userId, BotState.ASK_ACTIVITY);
        }

        if (botState.equals(BotState.ASK_ACTIVITY)) {
            if (isValidPhone(masterAnswer)) {
                masterDto.setPhone(masterAnswer);
                masterDto.setTelegramId(userId);
                replyToUser = messageService.getReplyMessageForService(chatId, "Выберите вид услуг: ");
                masterDataCache.setUsersCurrentBotState(userId, BotState.PROFILE_FIELD);
            } else
                replyToUser = messageService.getReplyMessage(chatId, "Некорректный номер телефона");
        }

        if (botState.equals(BotState.PROFILE_FIELD)) {
            if (update.hasCallbackQuery()) {
                masterDto.setActivity(masterAnswer);
                masterDataCache.setMasterInDb(masterDto);
                replyToUser = messageService.getReplyMessageForSchedule(chatId, "Составьте свое расписание: ");
            }
        }

        if (botState.equals(BotState.REGISTREDET)) {
            masterDto.setActivity(callbackService.getCallbackQuery(update.getCallbackQuery()).getText());
            masterDataCache.setMasterInDb(masterDto);
            replyToUser = messageService.getReplyMessageForSchedule(chatId, "Составьте свое расписание1: ");
        }

        masterDataCache.saveUserProfileData(userId, masterDto);
        return replyToUser;
    }

    private boolean isValidPhone(String phone) {
        Pattern pattern = Pattern.compile("^(\\+7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    @Override
    public ClientBotState getHandleClientName() {
        return null;
    }
}
