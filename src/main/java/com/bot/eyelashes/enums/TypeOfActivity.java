package com.bot.eyelashes.enums;

public enum TypeOfActivity {
    EYEBROWS("eyebrows"), EYELASH("eyelash"), NAILS("nails");
    private String activity;

    TypeOfActivity(String activity) {
        this.activity = activity;
    }

    public String getActivity() {
        return activity;
    }
}
