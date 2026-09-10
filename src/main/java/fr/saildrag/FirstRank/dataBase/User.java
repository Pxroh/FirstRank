package fr.saildrag.FirstRank.dataBase;

import fr.maxlego08.menu.api.MenuItemStack;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.requirement.Permissible;
import org.bukkit.entity.Player;

import java.util.*;

public class User {
    private final UUID uuid;
    private final String name;
    private final Map<String, UserStorage> virtualStorage = new HashMap<>();
    private MenuItemStack itemTrue;
    private MenuItemStack itemFalse;
    private List<Permissible> requirement;
    private List<Action> actions;
    private List<MenuItemStack> preview;

    public User(UUID uuid, String name){
        this.uuid = uuid;
        this.name = name;
    }
    public User(Player player){
        this(player.getUniqueId(), player.getName());
    }

    public UUID getUUID(){return uuid;}

    public String getName(){return name;}

    /*
     * Module VirtualStorage
     */

    public void addVirtualStorage(String id, UserStorage storage){
        this.virtualStorage.put(id,storage);
    }

    public UserStorage getVirtualStorage(String id) {
        return this.virtualStorage.computeIfAbsent(id, k -> new UserStorage(this.uuid, id, 0));
    }

    public void removeVirtualStorage(String id){
        virtualStorage.remove(id);
    }

    public Collection<UserStorage> getVirtualStorage(){
        return virtualStorage.values();
    }

    /*
     * Module Requirement
     */

    public void setItemTrue(MenuItemStack itemTrue) {
        this.itemTrue = itemTrue;
    }

    public MenuItemStack getItemTrue() {
        return itemTrue;
    }

    public void setItemFalse(MenuItemStack itemFalse) {
        this.itemFalse = itemFalse;
    }

    public MenuItemStack getItemFalse() {
        return itemFalse;
    }

    public void setRequirement(List<Permissible> requirement){
        this.requirement = requirement;
    }

    public List<Permissible> getRequirement(){
        return this.requirement;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<Action> getActions(){
        return this.actions;
    }
}
