package fr.saildrag.FirstRank.core;

import fr.maxlego08.menu.api.MenuItemStack;
import fr.maxlego08.menu.api.engine.InventoryEngine;
import fr.maxlego08.menu.api.engine.ItemButton;
import fr.maxlego08.menu.api.requirement.Permissible;
import fr.maxlego08.menu.api.requirement.permissible.PermissionPermissible;
import fr.maxlego08.menu.api.requirement.permissible.PlaceholderPermissible;
import fr.maxlego08.menu.api.utils.Placeholders;
import fr.saildrag.FirstRank.FirstRank;
import fr.saildrag.FirstRank.event.zMenuListener;
import fr.saildrag.FirstRank.permissible.requirement.VirtualStoragePermissible;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequirementManager {
    private static final File file = new File(FirstRank.getInstance().getDataFolder(), "requirement.yml");
    static private final Map<String, Map<String, RequirementItem>> requirementItem = new HashMap<>();
    static private List<String> tiny_lore;
    static private Map<String, ClickType> default_clicks = new HashMap<>();

    public static void loadConfig(FirstRank plugin) {
        requirementItem.clear();

        ConfigurationSection ConfigurationConfig = plugin.getConfig().getConfigurationSection("clicks");
        if (ConfigurationConfig == null) return;
        default_clicks.clear();
        ConfigurationConfig.getKeys(false).forEach(clicks -> {
            ConfigurationSection section = ConfigurationConfig.getConfigurationSection(clicks);
            String defaultValue = section.getString("default");
            String positiveMessage = section.getString("message.positive");
            String negativeMessage = section.getString("message.negative");
            default_clicks.put(clicks, new ClickType("default_clicks",defaultValue, positiveMessage, negativeMessage));
        });


        YamlConfiguration yaml = plugin.getUtils().getStorageYAML().loadConfig("requirement.yml", file);
        tiny_lore = yaml.getStringList("tiny_lore");

        Map<String, String> sections = Map.of(
                "placeholder", "%", // préfixe spécial pour placeholder
                "permissions", "",  // pas de préfixe pour permissions
                "virtualStorage", ""    // pas de préfixe pour quest items
        );

        sections.forEach((sectionName, prefix) -> {
            ConfigurationSection section = yaml.getConfigurationSection(sectionName);
            if (section == null) return;

            Map<String, RequirementItem> map = new HashMap<>();
            section.getKeys(false).forEach(key -> {
                String path = sectionName + "." + key + ".";
                if (sectionName == "placeholder"){
                    key = prefix + key + prefix;
                }
                if (sectionName == "permissions"){
                    key = key.replace("_", ".");
                }
                plugin.getUtils().getLogger().info("Le requirement "+sectionName+" à été chargé pour la valeur: "+key);
                map.put(key, getRequirementClass(path, yaml));
            });
            requirementItem.put(sectionName, map);
        });
    }

    private static RequirementItem getRequirementClass(String path, YamlConfiguration yaml){
        RequirementItem requirementClass = new RequirementItem();
        requirementClass.setItemFalse(zMenuListener.getInventoryManager().loadItemStack(yaml, path + "false.", file));
        requirementClass.setItemTrue(zMenuListener.getInventoryManager().loadItemStack(yaml, path + "true.", file));
        String tinyLoreConfigTrue = yaml.getString(path+"true.tiny_lore");
        requirementClass.setTinyLoreTrue(tinyLoreConfigTrue == null ? "a définir" : tinyLoreConfigTrue);
        String tinyLoreConfigFalse = yaml.getString(path+"false.tiny_lore");
        requirementClass.setTinyLoreFalse(tinyLoreConfigFalse == null ? "a définir" : tinyLoreConfigFalse);
        return requirementClass;
    }

    public static ClickType getDefault_clicks(String id) {
        return default_clicks.get(id);
    }

    public static List<String> getTiny_lore(){
        return tiny_lore;
    }

    public static Map<String, RequirementItem> getRequirementItem(String type){
        return requirementItem.get(type);
    }

    public static String getLoreRequirement(Permissible requirement, Player player){
        if (requirement instanceof PlaceholderPermissible requirementClass) {

            String value = getTinyLoreValue(getRequirementItem("placeholder").get(requirementClass.getPlaceholder()), player, requirement);
            value = value.replace("%value%", PlaceholderAPI.setPlaceholders(player, requirementClass.getPlaceholder()));
            value = value.replace("%value_requirement%",requirementClass.getValue());
            return value;

        } else if (requirement instanceof VirtualStoragePermissible requirementClass) {
            String value = getTinyLoreValue(getRequirementItem("virtualStorage").get(requirementClass.getStorage_Id()), player, requirement);
            value = value.replace("%value%",Integer.toString(requirementClass.getQuest_actuelCount(player.getUniqueId())));
            value = value.replace("%value_requirement%",Integer.toString(requirementClass.getQuest_count()));
            return value;

        } else if (requirement instanceof PermissionPermissible requirementClass) {
            return getTinyLoreValue(getRequirementItem("permissions").get(requirementClass.getPermission()), player, requirement);
        } else {
            return "Texte non définie dans la config !";
        }
    }

    public static void getItemRequirement(Integer slot, Permissible requirement, Player player, InventoryEngine inventory) {
        Placeholders placeholders = new Placeholders();
        String type;
        String id;
        if (requirement instanceof PlaceholderPermissible requirementClass) {
            type = "placeholder";
            id = requirementClass.getPlaceholder();
            placeholders.register("value", PlaceholderAPI.setPlaceholders(player, requirementClass.getPlaceholder()));
            placeholders.register("value_requirement", requirementClass.getValue());

        } else if (requirement instanceof VirtualStoragePermissible requirementClass) {
            type = "virtualStorage";
            placeholders.register("value", Integer.toString(requirementClass.getQuest_actuelCount(player.getUniqueId())));
            placeholders.register("value_requirement", Integer.toString(requirementClass.getQuest_count()));
            ItemButton button = addItem(
                    slot,
                    inventory,
                    getRequirementItem(type).get(requirementClass.getStorage_Id()),
                    player,
                    requirement,
                    placeholders,
                    requirementClass.getLore()
            );

            button.setMiddleClick(event -> {
                requirementClass.getMiddleClickActions().forEach(action -> action.preExecute(player, null, inventory, placeholders));
                inventory.getPlugin().getInventoryManager().updateInventory(player);
            });
            button.setLeftClick(event -> {
                requirementClass.getLeftClickActions().forEach(action -> action.preExecute(player, null, inventory, placeholders));
                inventory.getPlugin().getInventoryManager().updateInventory(player);
            });
            button.setRightClick(event -> {
                requirementClass.getRightClickActions().forEach(action -> action.preExecute(player, null, inventory, placeholders));
                inventory.getPlugin().getInventoryManager().updateInventory(player);
            });
            return;
        } else if (requirement instanceof PermissionPermissible requirementClass) {
            type = "permissions";
            id = requirementClass.getPermission();

        } else {
            inventory.addItem(slot, new ItemStack(Material.DIRT));
            return;
        }
        addItem(slot,inventory, getRequirementItem(type).get(id),player,requirement, placeholders, null);
    }

    private static String getTinyLoreValue(RequirementItem item, Player player, Permissible requirement) {
        if (item == null) return "§c[Item manquant dans la config]";

        if (requirement.hasPermission(player, null, zMenuListener.getInventoryManager().getFakeInventory(), new Placeholders())) {
            return item.getTinyLoreTrue();
        } else {
            return item.getTinyLoreFalse();
        }
    }

    private static ItemButton addItem(Integer slot, InventoryEngine inventory, RequirementItem item, Player player, Permissible requirement, Placeholders placeholders, List<String> lore){
        if (item == null) return inventory.addItem(slot, new ItemStack(Material.BEDROCK));
        if(requirement.hasPermission(player, null, zMenuListener.getInventoryManager().getFakeInventory(),new Placeholders())){
            return inventory.addItem(slot, item.getItemTrue().build(player, false, placeholders));
        } else {
            MenuItemStack menuItem = item.getItemFalse();
            ItemStack buildItem;
            if (lore != null){
                List<String> defaultLore = new ArrayList<>(menuItem.getLore());
                List<String> newLore = new ArrayList<>(menuItem.getLore());
                newLore.addAll(lore);
                menuItem.setLore(newLore);
                buildItem = menuItem.build(player, false, placeholders);
                menuItem.setLore(defaultLore);
            } else {
                buildItem = menuItem.build(player, false, placeholders);
            }
            return inventory.addItem(slot, buildItem);
        }
    }

    public static Boolean hasRequirementValue(Player player, List<Permissible> list){
        return list.stream().allMatch(permissible -> permissible.hasPermission(player, null, zMenuListener.getInventoryManager().getFakeInventory(), new Placeholders()));
    }

    public static long hasRequirementCount(Player player, List<Permissible> list){
        return list.stream()
                .filter(permissible -> permissible.hasPermission(player, null, zMenuListener.getInventoryManager().getFakeInventory(), new Placeholders()))
                .count();
    }
}
