package fr.saildrag.FirstRank.button;

import fr.maxlego08.menu.api.MenuItemStack;
import fr.maxlego08.menu.api.requirement.Permissible;
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

public class ButtonRequirementsFalse extends Button {

    @Override
    public void onClick(Player player, InventoryClickEvent event, InventoryEngine inventory, int slot, Placeholders placeholders) {
        super.onClick(player, event, inventory, slot, placeholders);
    }

    @Override
    public ItemStack getCustomItemStack(Player player) {
        User user = FirstRank.getInstance().getUserManager().getUser(player);
        MenuItemStack item = user.getItemFalse();
        item = item != null ? item : this.getItemStack();

        List<Permissible> requirementlist = user.getRequirement();
        Placeholders placeholders = new Placeholders();
        placeholders.register("requirement_true", Long.toString(RequirementManager.hasRequirementCount(player, requirementlist)));
        placeholders.register("requirement_count", Long.toString(requirementlist.size()));

        return item.build(player, false, placeholders);
    }
}