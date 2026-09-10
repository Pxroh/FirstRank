package fr.saildrag.FirstRank.core;

import fr.saildrag.FirstRank.FirstRank;

public class ClickType {
    private final String type;
    private final String value;
    private final String message_positive;
    private final String message_negative;

    public ClickType(String type, String defaultValue, String messagePositive, String messageNegative){
        this.type = type;
        this.value = defaultValue;
        this.message_positive = messagePositive.replace("%"+type+"_click%",value);
        this.message_negative = messageNegative;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getMessagePositive() {
        return message_positive;
    }

    public String getMessageNegative() {
        return message_negative;
    }

    public String getLore(){
        return this.message_positive;
    }
}
