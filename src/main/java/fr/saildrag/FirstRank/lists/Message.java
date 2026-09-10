package fr.saildrag.FirstRank.lists;

import fr.saildrag.library.LibraryEnum;

public enum  Message implements LibraryEnum {
    NOPERMISSION("You don't have the permisisons for this"), //REQUIRED !
    ONLYPLAYER("You can't for this"), //REQUIRED !
    COMMAND_USAGE("Usage: /%command%"), //REQUIRED !
    COMMAND_RELOAD("Plugin reload sucess !");

    private final String path;
    private String message;
    Message(String message) {
        this.path = this.name().toLowerCase().replace("_", ".");
        this.message = message;
    }
    @Override
    public void setValue(String value){
        this.message = value;
    }
    @Override
    public String getPath() {
        return path;
    }
    @Override
    public String toString() {
        return message;
    }
}
