package com.bot.eyelashes.handler.callbackquery;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service("CallbackRegistrationMaster")
@RequiredArgsConstructor
public class CallbackRegistrationMasterImpl implements CallbackRegistration {
    private final MasterDataCache masterDataCache;
    private final MessageService messageService;
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        if (masterDataCache.getUsersCurrentBotState(callbackQuery.getMessage().getFrom().getId()).equals(BotState.FILLING_PROFILE)) {
            masterDataCache.setUsersCurrentBotState(callbackQuery.getMessage().getFrom().getId(), BotState.ASK_FULL_NAME);
        }
        return processUsersInput(callbackQuery.getMessage());
    }

    @Override
    public BotState getHandleName() {
        return BotState.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Message inputMessage) {
        String usersAnswer = inputMessage.getText();
        Long userId = inputMessage.getFrom()
                .getId();
        Long chatId = inputMessage.getChatId();

        MasterDto masterDto = masterDataCache.getUserProfileData(userId);
        BotState botState = masterDataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.ASK_FULL_NAME)) {
            replyToUser = messageService.getReplyMessage(chatId, "Введите ФИО");
            masterDataCache.setUsersCurrentBotState(userId, BotState.ASK_PHONE);
        }
        if (botState.equals(BotState.ASK_PHONE)) {
            String[] fullName = usersAnswer.split(" ");
            masterDto.setName(fullName[1]);
            masterDto.setSurname(fullName[0]);
            masterDto.setMiddleName(fullName[2]);
            replyToUser = messageService.getReplyMessage(chatId, "Введите номер телефона: ");
            masterDataCache.setUsersCurrentBotState(userId, BotState.ASK_ACTIVITY);
        }

        masterDataCache.saveUserProfileData(userId, masterDto);
        return replyToUser;

    }

}
