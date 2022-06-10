package com.bot.eyelashes.cache;

import com.bot.eyelashes.enums.BotState;
import com.bot.eyelashes.model.dto.MasterDto;

public interface DataCache {
    void setUsersCurrentBotState(Long userId, BotState botState);

    BotState getUsersCurrentBotState(Long userId);

    MasterDto getUserProfileData(Long userId);

    void saveUserProfileData(Long userId, MasterDto masterDto);

}
