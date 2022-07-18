package com.bot.eyelashes.handler.callbackquery.impl;

import com.bot.eyelashes.handler.callbackquery.Callback;
import com.bot.eyelashes.model.entity.Client;
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

@Service("InfoClientForMasterCallback")
@RequiredArgsConstructor
public class InfoClientForMasterCallback implements Callback {
    private final ClientRepository clientRepository;
    private final MessageService messageService;
    private final RecordToMasterRepository recordToMasterRepository;
    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        long clientIdInCallback = Integer.parseInt(callbackQuery.getData().split("/")[1]);
        Optional<Client> client = clientRepository.findByTelegramId(clientIdInCallback);
        String telegramNickClient = client.get().getTelegramNick();
        return messageService.getReplyMessageWithKeyboard(callbackQuery.getMessage().getChatId(),
                "Вы хотите связаться с клиентом\uD83D\uDCDE или перенести дату записи\uD83D\uDCC5",
                keyboard(telegramNickClient, clientIdInCallback));
    }

    private InlineKeyboardMarkup keyboard(String nickname, Long clientId) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(List.of(
                InlineKeyboardButton.builder().text("Изменить дату").callbackData("REPLACE_RECORD/" + clientId)
                        .build(),
                InlineKeyboardButton.builder().text("Связаться").url("https://t.me/"+ nickname).build()
        ));

        return InlineKeyboardMarkup.builder().keyboard(buttons).build();
    }
}
