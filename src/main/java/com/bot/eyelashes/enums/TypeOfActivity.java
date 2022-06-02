package com.bot.eyelashes.enums;

public enum TypeOfActivity {
    EYEBROWS("Брови"), EYELASH("Ресницы"), NAILS("Ногти");
    private String activity;

    TypeOfActivity(String activity) {
        this.activity = activity;
    }

    public String getActivity() {
        return activity;
    }
}
