package fr.saildrag.FirstRank.lists;

import fr.saildrag.library.LibraryEnum;
import fr.saildrag.library.commands.BaseCommand;
import fr.saildrag.FirstRank.commands.reloadCommand;
import fr.saildrag.FirstRank.FirstRank;
import org.bukkit.plugin.java.JavaPlugin;

public enum ListCommand implements LibraryEnum {
    RELOAD(reloadCommand.class);

    private final String id;

    private final Class<? extends BaseCommand<?>> commandClass;

    private String permission;

    ListCommand(Class<? extends BaseCommand<?>> commandClass) {
        this.id = this.name().toLowerCase();
        this.commandClass = commandClass;
    }

    @Override
    public BaseCommand<?> createInstance(JavaPlugin plugin) {
        try {
            return commandClass.getDeclaredConstructor(FirstRank.class, String.class).newInstance(plugin, this.id);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Impossible de créer l'instance de commande pour " + name());
        }
    }

    public String getId() {
        return id;
    }

    public void setPermission(String value){
        this.permission = value;
    }

    public String getPermission(){
        return permission;
    }
    @Override
    public String toString() {
        return this.name();
    }

    @Override
    public String getPath() {
        return this.id;
    }
}
