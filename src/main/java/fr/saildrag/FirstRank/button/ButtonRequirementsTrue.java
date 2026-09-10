package fr.saildrag.FirstRank.button;

import fr.maxlego08.menu.api.MenuItemStack;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.Placeholders;
import fr.maxlego08.menu.api.button.Button;
import fr.maxlego08.menu.api.engine.InventoryEngine;
import fr.saildrag.FirstRank.dataBase.User;
import fr.saildrag.FirstRank.core.RequirementManager;
import fr.saildrag.FirstRank.FirstRank;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ButtonRequirementsTrue extends Button {

    @Override
    public void onClick(Player player, InventoryClickEvent event, InventoryEngine inventory, int slot, Placeholders placeholders) {
        super.onClick(player, event, inventory, slot, placeholders);
        List<Action> actions = FirstRank.getInstance().getUserManager().getUser(player).getActions();
        actions.forEach(action -> action.preExecute(player,this, inventory, new Placeholders()));
    }

    @Override
    public ItemStack getCustomItemStack(Player player) {
        User user = FirstRank.getInstance().getUserManager().getUser(player);
        MenuItemStack item = user.getItemTrue();
        item = item != null ? item : this.getItemStack();

        Placeholders placeholders = new Placeholders();
        placeholders.register("requirement_count", Long.toString(user.getRequirement().size()));
        return item.build(player, false, placeholders);
    }

    @Override
    public boolean hasPermission() {
        return true;
    }

    @Override
    public boolean checkPermission(Player player, InventoryEngine inventory, Placeholders placeholders) {
        return RequirementManager.hasRequirementValue(player, FirstRank.getInstance().getUserManager().getUser(player).getRequirement());
    }
}