package com.bot.eyelashes.handler.callbackquery;

import com.bot.eyelashes.cache.MasterDataCache;
import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.enums.map.ActivityId;
import com.bot.eyelashes.model.dto.MasterActivityDto;
import com.bot.eyelashes.model.dto.MasterDto;
import com.bot.eyelashes.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service("CallbackService")
@RequiredArgsConstructor
public class CallbackServiceImpl implements Callback {
    private final MasterDataCache masterDataCache;
    private final MessageService messageService;

    @Override
    public SendMessage getCallbackQuery(CallbackQuery callbackQuery) {
        MasterActivityDto masterActivityDto = new MasterActivityDto();
        ActivityId activityId = new ActivityId();
        masterDataCache.setUsersCurrentBotState(callbackQuery.getMessage()
                .getChatId(), BotState.REGISTERED);
        MasterDto userProfileData = masterDataCache.getUserProfileData(callbackQuery.getMessage()
                .getChatId());
        userProfileData.setActivity(callbackQuery.getData());
        masterActivityDto.setMasterId(userProfileData.getTelegramId());
        masterActivityDto.setActivityId(activityId.getIdByName(callbackQuery.getData()));

        masterDataCache.setMasterInDb(userProfileData);
        return messageService.getReplyMessageForSchedule(callbackQuery.getMessage()
                .getChatId(), "Составьте свое расписание");
    }

    private void setMasterAcitivity(MasterActivityDto masterActivityDto) {

    }
}
