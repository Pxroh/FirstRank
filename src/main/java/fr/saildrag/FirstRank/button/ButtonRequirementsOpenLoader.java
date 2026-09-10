package fr.saildrag.FirstRank.button;

import fr.maxlego08.menu.api.MenuItemStack;
import fr.maxlego08.menu.api.button.Button;
import fr.maxlego08.menu.api.button.DefaultButtonValue;
import fr.maxlego08.menu.api.loader.ButtonLoader;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.requirement.Permissible;
import fr.maxlego08.menu.zcore.utils.itemstack.MenuItemStackFormMap;
import fr.saildrag.FirstRank.FirstRank;
import fr.saildrag.FirstRank.event.zMenuListener;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ButtonRequirementsOpenLoader extends ButtonLoader {
    private final FirstRank plugin;
    public ButtonRequirementsOpenLoader(FirstRank plugin) {
        super(plugin, "requirement_open");
        this.plugin = plugin;
    }

    @Override
    public Button load(YamlConfiguration yamlConfiguration, String s, DefaultButtonValue defaultButtonValue) {
        String inventorie = yamlConfiguration.getString(s + "inventorie");
        List<Action> actions = zMenuListener.getButtonManager().loadActions((List<Map<String, Object>>) yamlConfiguration.getList(s + "actions_true", new ArrayList<>()), s, defaultButtonValue.getFile());
        List<Permissible> requirements = zMenuListener.getButtonManager().loadPermissible((List<Map<String, Object>>) yamlConfiguration.getList(s + "requirements", new ArrayList<>()), s, defaultButtonValue.getFile());
        MenuItemStack item_true = zMenuListener.getInventoryManager().loadItemStack(yamlConfiguration, s + "item_true.", defaultButtonValue.getFile());
        MenuItemStack item_false = zMenuListener.getInventoryManager().loadItemStack(yamlConfiguration, s + "item_false.", defaultButtonValue.getFile());

        return new ButtonRequirementsOpen(this.plugin, item_true, item_false, actions, requirements, inventorie);
    }
}
