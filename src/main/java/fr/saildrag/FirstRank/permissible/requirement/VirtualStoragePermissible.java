package fr.saildrag.FirstRank.permissible.requirement;

import fr.maxlego08.menu.api.button.Button;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.Placeholders;
import fr.maxlego08.menu.api.engine.InventoryEngine;
import fr.saildrag.FirstRank.FirstRank;
import fr.saildrag.FirstRank.core.ClickType;
import fr.saildrag.FirstRank.permissible.requirement.virtualStorage.IVirtualPermissible;
import fr.saildrag.FirstRank.dataBase.UserStorage;
import fr.saildrag.FirstRank.permissible.ZPermissible;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VirtualStoragePermissible extends ZPermissible {
    private final FirstRank plugin;
    private final String quest_id;
    private final IVirtualPermissible virtualPermissible;
    private final Map<String, ClickType> clickType;

    public VirtualStoragePermissible(
            FirstRank plugin,
            String quest_id,
            IVirtualPermissible virtualPermissible,
            Map<String, ClickType> clickType,
            List<Action> denyActions,
            List<Action> successActions,
            List<Action> leftClickActions,
            List<Action> rightClickActions,
            List<Action> middleClickAction
    ) {
        super(denyActions, successActions, leftClickActions, rightClickActions, middleClickAction);
        this.plugin = plugin;
        this.quest_id = quest_id;
        this.virtualPermissible = virtualPermissible;
        this.clickType = clickType;
    }

    @Override
    public boolean hasPermission(Player player, Button button, InventoryEngine InventoryEngine, Placeholders placeholders) {
        UserStorage userStorage = this.plugin.getUserManager().getUser(player).getVirtualStorage(this.quest_id);
        return userStorage.getValue() >= virtualPermissible.getQuest_count();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public int getQuest_count() {
        return virtualPermissible.getQuest_count();
    }

    public String getQuest_Id() {
        return this.quest_id;
    }

    public int getQuest_actuelCount(UUID uuid) {
        return this.plugin.getUserManager().getUser(uuid).getVirtualStorage(this.quest_id).getValue();
    }

    public String getStorage_Id() {
        return virtualPermissible.getId();
    }

    public IVirtualPermissible getVirtualPermissible() {
        return virtualPermissible;
    }

    public List<String> getLore(){
        List<String> lore = new ArrayList<>();
        lore.add(getClickType("left").getLore());
        lore.add(getClickType("right").getLore());
        lore.add(getClickType("middle").getLore());
        return lore;
    }

    public ClickType getClickType(String clickType) {
        return this.clickType.get(clickType);
    }
}
