package fr.saildrag.FirstRank.permissible.action;

import fr.maxlego08.menu.api.button.Button;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.Placeholders;
import fr.maxlego08.menu.api.engine.InventoryEngine;
import fr.saildrag.FirstRank.FirstRank;
import org.bukkit.entity.Player;


public class StockRemoveAction extends Action {
    private final FirstRank plugin;
    private final String quest_id;

    public StockRemoveAction(FirstRank plugin, String questId) {
        this.plugin = plugin;
        this.quest_id = questId;
    }

    @Override
    protected void execute(Player player, Button button, InventoryEngine inventory, Placeholders placeholders) {
        this.plugin.getUserManager().getUser(player).removeVirtualStorage(this.quest_id);
    }
}
