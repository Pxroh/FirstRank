package fr.saildrag.FirstRank.event;

import fr.maxlego08.menu.api.ButtonManager;
import fr.maxlego08.menu.api.InventoryManager;
import fr.maxlego08.menu.api.command.CommandManager;
import fr.maxlego08.menu.api.event.events.ButtonLoaderRegisterEvent;
import fr.maxlego08.menu.api.exceptions.InventoryException;
import fr.maxlego08.menu.api.loader.NoneLoader;
import fr.saildrag.FirstRank.FirstRank;
import fr.saildrag.FirstRank.permissible.action.StockAddLoader;
import fr.saildrag.FirstRank.permissible.action.StockRemoveLoader;
import fr.saildrag.FirstRank.permissible.requirement.VirtualStoragePermissibleLoader;
import fr.saildrag.FirstRank.button.ButtonRequirementsFalse;
import fr.saildrag.FirstRank.button.ButtonRequirementsOpenLoader;
import fr.saildrag.FirstRank.button.ButtonRequirementsShow;
import fr.saildrag.FirstRank.button.ButtonRequirementsTrue;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;

public class zMenuListener implements Listener {
    private static FirstRank plugin;
    private static InventoryManager inventoryManager;
    private static ButtonManager buttonManager;
    private static CommandManager commandManager;

    public zMenuListener(FirstRank plugin) {
        zMenuListener.plugin = plugin;
    }

    public static ButtonManager getButtonManager() {
        return buttonManager;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    @EventHandler
    public void onLoad(ButtonLoaderRegisterEvent event) {
        buttonManager = event.getButtonManager();
        inventoryManager = event.getInventoryManager();
        commandManager = plugin.getProvider(CommandManager.class);

        //Requirement
        event.register(new NoneLoader(plugin, ButtonRequirementsShow.class, "requirement_show"));
        event.register(new NoneLoader(plugin, ButtonRequirementsTrue.class, "requirement_true"));
        event.register(new NoneLoader(plugin, ButtonRequirementsFalse.class, "requirement_false"));
        event.register(new ButtonRequirementsOpenLoader(plugin));
        //Item Stock
        buttonManager.registerPermissible(new VirtualStoragePermissibleLoader(plugin, buttonManager));
        buttonManager.registerAction(new StockRemoveLoader(plugin));
        buttonManager.registerAction(new StockAddLoader(plugin));

        plugin.loadConfig();
    }

    public static void loadInventory(File file) {
        try {
            inventoryManager.loadInventory(plugin, file);
        } catch (InventoryException e1) {
            e1.printStackTrace();
        }
    }
}
