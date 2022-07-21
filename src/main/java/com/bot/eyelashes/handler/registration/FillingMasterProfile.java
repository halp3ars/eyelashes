package com.bot.eyelashes.handler.registration;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.ClientBotState;
import com.bot.eyelashes.enums.map.HashMapDayOfWeekModeService;
import com.bot.eyelashes.handler.impl.HandleMasterActivityImpl;
import com.bot.eyelashes.handler.impl.HandleMasterScheduleImpl;
import com.bot.eyelashes.handler.impl.HandleMasterTimeFromImpl;
import com.bot.eyelashes.handler.impl.HandleMasterTimeToImpl;
import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.model.dto.ScheduleDto;
import com.bot.eyelashes.model.entity.Master;
import com.bot.eyelashes.model.entity.Schedule;
import com.bot.eyelashes.repository.MasterRepository;
import com.bot.eyelashes.repository.ScheduleRepository;
import com.bot.eyelashes.schedule.service.ScheduleService;
import com.bot.eyelashes.service.Bot;
import com.bot.eyelashes.service.MessageService;
import com.bot.eyelashes.validation.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FillingMasterProfile implements HandleRegistration {
    private final MasterDataCache masterDataCache;
    private final MessageService messageService;
    private final HashMapDayOfWeekModeService dayOfWeekModeService;
    private final ScheduleService scheduleService;

    @Override
    public SendMessage getMessage(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandleName() {
        return BotState.FILLING_PROFILE;
    }


    public SendMessage processUsersInput(Message inputMessage) {
        String usersAnswer = inputMessage.getText();
        Long chatId = inputMessage.getChatId();
        MasterDto masterDto = masterDataCache.getUserProfileData(chatId);
        BotState botState = masterDataCache.getUsersCurrentBotState(chatId);
        ScheduleDto userScheduleData = masterDataCache.getUserScheduleData(inputMessage.getChatId());
        SendMessage replyToUser = null;

        if (botState.equals(BotState.ASK_NAME)) {
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_SURNAME);
            replyToUser = messageService.getReplyMessage(chatId, "Введите Ваше имя");
            masterDto.setTelegramId(chatId);

        }

        if (botState.equals(BotState.ASK_SURNAME)) {
            if (Validation.isValidText(usersAnswer)) {
                masterDto.setName(usersAnswer);
                log.info("master set name = " + usersAnswer);
                replyToUser = messageService.getReplyMessage(chatId, "Введите Вашу фамилию");
                masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_ACTIVITY);
            } else {
                replyToUser = messageService.getReplyMessage(chatId, "Допустимы только буквы латинского и русского алфавита");
            }
        }

        if (botState.equals(BotState.ASK_ACTIVITY)) {
            if (Validation.isValidText(usersAnswer)) {
                masterDto.setSurname(usersAnswer);
                log.info("master set surname = " + usersAnswer);
                masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_ADDRESS);
                HandleMasterActivityImpl handleTypeOfActivity = new HandleMasterActivityImpl();
                replyToUser = messageService.getReplyMessageWithKeyboard(chatId, "Выберите вид деятельности",
                        handleTypeOfActivity.createInlineKeyboard());

            } else {
                replyToUser = messageService.getReplyMessage(chatId, "Допустимы только буквы латинского и русского алфавита");
            }
        }
        if (botState.equals(BotState.ASK_ADDRESS)) {
            masterDto.setTelegramNick(inputMessage.getChat()
                    .getUserName());
            log.info("master set telegramNickName = " + inputMessage.getChat()
                    .getUserName());
            replyToUser = messageService.getReplyMessage(chatId, "Введите адрес");
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_PHONE);
        }
        if (botState.equals(BotState.ASK_PHONE)) {
            masterDto.setAddress(usersAnswer);
            log.info("master set address = " + usersAnswer);
            replyToUser = messageService.getReplyMessageForContact(chatId, "Отправьте свой номер телефона: ");
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_DAY);
        }

        if (botState.equals(BotState.ASK_DAY)) {
            HandleMasterScheduleImpl handleMasterSchedule = new HandleMasterScheduleImpl(dayOfWeekModeService);
            if (inputMessage.getContact() == null) {
                masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_DAY);
            } else {
                masterDataCache.setUsersCurrentBotState(chatId, botState);
                log.info("master set phoneNumber = " + inputMessage.getContact()
                        .getPhoneNumber());
                masterDto.setPhone(inputMessage.getContact().getPhoneNumber());
            }
            masterDataCache.setUsersCurrentBotState(chatId, BotState.REGISTERED);
            /*handleMasterSchedule.generateKeyboardWithText1(chatId)*/
            replyToUser = messageService.getReplyMessageWithKeyboard(chatId, "Выберите дни",
                    handleMasterSchedule.generateKeyboardWithText1(chatId));
        }

        if (botState.equals(BotState.ASK_TIME_FROM)) {
            masterDataCache.setUsersCurrentBotState(chatId, BotState.ASK_TIME_TO);
            HandleMasterTimeFromImpl handleMasterTimeFrom = new HandleMasterTimeFromImpl();
            replyToUser = messageService.getReplyMessageWithKeyboard(chatId, "Выберите начало рабочего дня",
                    handleMasterTimeFrom.createInlineKeyboard());
        }
        if (botState.equals(BotState.ASK_TIME_TO)) {
            HandleMasterTimeToImpl handleMasterTimeTo = new HandleMasterTimeToImpl();
            masterDataCache.setUsersCurrentBotState(chatId, BotState.REGISTERED);
            replyToUser = messageService.getReplyMessageWithKeyboard(chatId, "Выберите конец рабочего дня",
                    handleMasterTimeTo.createInlineKeyboard());
        }
        if (botState.equals(BotState.REGISTERED)) {
                masterDataCache.setMasterInDb(masterDto);
                userScheduleData.setTelegramId(chatId);
                log.info("data master save in db");

                replyToUser = messageService.getReplyMessageWithKeyboard(chatId, "Вы зарегистрированы.\n",
                        createFinalButton());
            masterDataCache.setScheduleInDb(userScheduleData);
//            scheduleService.setMessage(replyToUser);
                Bot.masterRegistration = false;
                masterDataCache.setUsersCurrentBotState(chatId, BotState.NONE);
        }

        masterDataCache.saveUserProfileData(chatId, masterDto);

        return replyToUser;
    }
    private InlineKeyboardMarkup createButtonForSchedule() {
        WebAppInfo webAppInfo = WebAppInfo.builder().url("https://192.168.111.159:3000").build();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Collections.singletonList(InlineKeyboardButton.builder()
                .text("Расписание")
                .webApp(webAppInfo)
                .build()));

        return InlineKeyboardMarkup.builder().keyboard(buttons).build();
    }

    private InlineKeyboardMarkup createFinalButton() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(InlineKeyboardButton.builder()
                .text("Меню")
                .callbackData("MASTER")
                .build()));
        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

    @Override
    public ClientBotState getHandleClientName() {
        return null;
    }
}
