package fr.saildrag.FirstRank.button;

import fr.maxlego08.menu.api.MenuItemStack;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.requirement.Permissible;
import fr.maxlego08.menu.api.utils.Placeholders;
import fr.maxlego08.menu.api.button.Button;
import fr.maxlego08.menu.api.engine.InventoryEngine;
import fr.saildrag.FirstRank.dataBase.User;
import fr.saildrag.FirstRank.event.zMenuListener;
import fr.saildrag.FirstRank.core.RequirementManager;
import fr.saildrag.FirstRank.FirstRank;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ButtonRequirementsOpen extends Button {
    private final FirstRank plugin;
    private final MenuItemStack itemTrue;
    private final MenuItemStack itemFalse;
    private final List<Action> actions;
    private final List<Permissible> requirements;
    private final String inventorie;
    private List<String> defaultLore = null;

    public ButtonRequirementsOpen(FirstRank plugin, MenuItemStack itemTrue, MenuItemStack itemFalse, List<Action> actions, List<Permissible> requirements, String inventorie) {
        this.plugin = plugin;
        this.itemTrue = itemTrue;
        this.itemFalse = itemFalse;
        this.actions = actions;
        this.requirements = requirements;
        this.inventorie = inventorie;
    }

    @Override
    public ItemStack getCustomItemStack(Player player) {
        if (defaultLore == null) {
            defaultLore = this.getItemStack().getLore();
        }

        List<String> customLore = new ArrayList<>(defaultLore);
        for (Permissible requirement : this.requirements) {
            customLore.add(RequirementManager.getLoreRequirement(requirement, player));
        }
        customLore.addAll(RequirementManager.getTiny_lore());

        MenuItemStack item = this.getItemStack();
        item.setLore(customLore);
        return item.build(player, false);
    }
    @Override
    public void onClick(Player player, InventoryClickEvent event, InventoryEngine inventory, int slot, Placeholders placeholders) {
        User user = this.plugin.getUserManager().getUser(player);
        user.setItemTrue(this.itemTrue);
        user.setItemFalse(this.itemFalse);
        user.setRequirement(this.requirements);
        user.setActions(this.actions);
        zMenuListener.getInventoryManager().openInventory(player,inventorie);
    }
}
