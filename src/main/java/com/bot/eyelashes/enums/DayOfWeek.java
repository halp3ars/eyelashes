package com.bot.eyelashes.enums;

public enum DayOfWeek {
    MONDAY("Понедельник"), TUESDAY("Вторник"), WEDNESDAY("Среда"), THURSDAY("Четверг"),
    FRIDAY("Пятница"), SATURDAY("Суббота"), SUNDAY("Воскресенье"), MASTER_TIME("Готово"), NONE("None");

    private final String DAY;
    DayOfWeek(String day) {
        DAY = day;
    }

    public String getNameDay(){
        return DAY;
    }
}
