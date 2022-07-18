package com.bot.eyelashes.handler.callbackquery;

import com.bot.eyelashes.enums.map.ScheduleMasterMap;
import com.bot.eyelashes.model.entity.Client;
import com.bot.eyelashes.model.entity.RecordToMaster;
import com.bot.eyelashes.repository.ClientRepository;
import com.bot.eyelashes.repository.RecordToMasterRepository;
import com.bot.eyelashes.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("ListClientByDayCallback")
@RequiredArgsConstructor
public class ListClientByDayCallback implements Callback {
    private final RecordToMasterRepository recordToMasterRepository;
    private final ClientRepository clientRepository;
    private final MessageService messageService;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        return processInputCallback(callbackQuery);
    }

    private SendMessage processInputCallback(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        ScheduleMasterMap scheduleMasterMap = new ScheduleMasterMap();
        String day = scheduleMasterMap.getDay(callbackQuery.getData().split("/")[1]);

        List<RecordToMaster> recordedClient = recordToMasterRepository.findByDayAndMasterId(day, chatId);
        InlineKeyboardMarkup keyboard = generateKeyboardClientByDay(recordedClient);
        if (recordedClient.size() == 0) {
            return messageService.getReplyMessageWithKeyboard(chatId, "Записаных клиентов на " + day +
                            " не найдено.\nВернитесь на главное меню", keyboardForEmptyListClient());
        }

        return messageService.getReplyMessageWithKeyboard(chatId, "Клиенты записанные на " + day, keyboard);
    }

    private InlineKeyboardMarkup generateKeyboardClientByDay(List<RecordToMaster>  recordedClient) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        for (RecordToMaster record : recordedClient) {
            Optional<Client> client = clientRepository.findByTelegramId(record.getClientId());
            if (record.getClientId().equals(client.get().getTelegramId())) {
                buttons.add(List.of(
                        InlineKeyboardButton.builder()
                                .text(client.get()
                                        .getName() + " " + client.get()
                                        .getSurname() + " записан на "
                                        + record.getTime() + " : 00")
                                .callbackData("RECORDED_CLIENT/" + client.get()
                                        .getTelegramId())
                                .build()
                ));
            }
        }

        return InlineKeyboardMarkup.builder().keyboard(buttons).build();
    }

    private InlineKeyboardMarkup keyboardForEmptyListClient() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Понедельник")
                        .callbackData("DAY_CLIENT_RECORD_TO_MASTER/MONDAY")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Вторник")
                        .callbackData("DAY_CLIENT_RECORD_TO_MASTER/TUESDAY")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Среда")
                        .callbackData("DAY_CLIENT_RECORD_TO_MASTER/WEDNESDAY")
                        .build()
        ));

        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Четверг")
                        .callbackData("DAY_CLIENT_RECORD_TO_MASTER/THURSDAY")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Пятница")
                        .callbackData("DAY_CLIENT_RECORD_TO_MASTER/FRIDAY")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Суббота")
                        .callbackData("DAY_CLIENT_RECORD_TO_MASTER/SATURDAY")
                        .build()

        ));

        buttons.add(List.of(
                InlineKeyboardButton.builder()
                        .text("Воскресенье")
                        .callbackData("DAY_CLIENT_RECORD_TO_MASTER/SUNDAY")
                        .build(),
                InlineKeyboardButton.builder()
                        .text("Меню")
                        .callbackData("MENU")
                        .build()
        ));

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }
}
