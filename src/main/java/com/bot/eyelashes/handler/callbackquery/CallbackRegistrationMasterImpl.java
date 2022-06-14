package com.bot.eyelashes.handler.callbackquery;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.handler.BotStateHandleContext;
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
    private final BotStateHandleContext botStateHandleContext;
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        if (masterDataCache.getUsersCurrentBotState(callbackQuery.getMessage().getFrom().getId()).equals(BotState.FILLING_PROFILE)) {
            masterDataCache.setUsersCurrentBotState(callbackQuery.getMessage().getFrom().getId(), BotState.ASK_FULL_NAME);
        }
        return processUsersInput(callbackQuery.getMessage());
    }

    @Override
    public SendMessage getMessage(Message message) {
        if (masterDataCache.getMessageCurrentState(message.getFrom().getId()).equals(BotState.ASK_FULL_NAME)) {
            masterDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_PHONE);
        }
        return processUsersInput(message);
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
        if (botState.equals(BotState.ASK_ACTIVITY)) {
            masterDto.setPhone(usersAnswer);
            masterDto.setTelegramId(userId);
            replyToUser = messageService.getReplyMessage(chatId, "Введите услуги: ");
            masterDataCache.setUsersCurrentBotState(userId, BotState.PROFILE_FIELD);
        }
        if (botState.equals(BotState.PROFILE_FIELD)) {
            masterDto.setActivity(usersAnswer);
            masterDataCache.setUsersCurrentBotState(userId, BotState.REGISTREDET);
            replyToUser = SendMessage.builder()
                    .text(String.format("%s %s", "Данные по вашей анкете\n", masterDto))
                    .chatId(chatId.toString())
                    .build();
        }
        if (botState.equals(BotState.REGISTREDET)) {
            masterDataCache.setMasterInDb(masterDto);
            replyToUser = SendMessage.builder()
                    .text("Для записи графика работы\nВведите Расписание")
                    .chatId(userId.toString())
                    .build();
        }


        masterDataCache.saveUserProfileData(userId, masterDto);
        return replyToUser;

    }

}
