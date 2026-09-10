package fr.saildrag.FirstRank.dataBase;

import java.util.UUID;

public class UserStorage {
    private final UUID uuid;
    private final String id;
    private int value;

    public UserStorage(UUID uuid, String id, int value) {
        this.uuid = uuid;
        this.id = id;
        this.value = value;
    }

    public UUID getPlayerId() {
        return this.uuid;
    }

    public String getId() {
        return this.id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public void addValue(int value){
        this.value = Math.max(0, this.value + value);
    }

    public void removeValue(int value){
        this.value = Math.max(0, this.value - value);
    }
}
