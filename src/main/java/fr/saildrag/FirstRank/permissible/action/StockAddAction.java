package fr.saildrag.FirstRank.permissible.action;

import fr.maxlego08.menu.api.button.Button;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.Placeholders;
import fr.maxlego08.menu.api.engine.InventoryEngine;
import fr.saildrag.FirstRank.FirstRank;
import fr.saildrag.FirstRank.permissible.requirement.virtualStorage.IVirtualPermissible;
import fr.saildrag.FirstRank.dataBase.UserStorage;
import org.bukkit.entity.Player;


public class StockAddAction extends Action {
    private final FirstRank plugin;
    private final IVirtualPermissible virtualPermissible;
    private final String quest_id;

    public StockAddAction(FirstRank plugin, IVirtualPermissible virtualPermissible, String quest_id) {
        this.plugin = plugin;
        this.virtualPermissible = virtualPermissible;
        this.quest_id = quest_id;
    }

    @Override
    protected void execute(Player player, Button button, InventoryEngine inventory, Placeholders placeholders) {
        UserStorage userStorage = this.plugin.getUserManager().getUser(player).getVirtualStorage(this.quest_id);
        userStorage.addValue(virtualPermissible.execute(player, userStorage));
    }

    public IVirtualPermissible getVirtualPermissible() {
        return virtualPermissible;
    }
}
