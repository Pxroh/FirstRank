package fr.saildrag.FirstRank.permissible.requirement.virtualStorage;

import fr.maxlego08.menu.api.MenuItemStack;
import fr.maxlego08.menu.api.utils.TypedMapAccessor;
import fr.saildrag.FirstRank.dataBase.UserStorage;
import fr.saildrag.FirstRank.event.zMenuListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.File;
import java.util.Map;

public class VirtualMaterial extends VirtualPermissible {
    private MenuItemStack menuItemStack;

    public VirtualMaterial() {
        super("wait");
    }

    @Override
    public void load(String path, TypedMapAccessor accessor, File file) {
        super.load(path, accessor, file);
        this.menuItemStack = zMenuListener.getInventoryManager().loadItemStack(file, path, (Map<String, Object>) accessor.getObject("item"));
        setId(menuItemStack.getMaterial());
    }

    @Override
    public int execute(Player player, UserStorage userStorage) {

        ItemStack itemToRemove = this.menuItemStack.build(player);
        int neededToRemove = Math.min(this.quest_count - userStorage.getValue(), count);
        int removed = 0;

        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            if (neededToRemove <= 0) break;

            ItemStack currentItem = inventory.getItem(i);
            if (currentItem == null || !isSimilarItem(currentItem, itemToRemove)) continue;

            int currentAmount = currentItem.getAmount();

            if (currentAmount > neededToRemove) {
                currentItem.setAmount(currentAmount - neededToRemove);
                removed += neededToRemove;
                neededToRemove = 0;
            } else {
                removed += currentAmount;
                neededToRemove -= currentAmount;
                inventory.setItem(i, new ItemStack(Material.AIR)); // ou setAmount(0);
            }
        }
        return removed;
    }


    private boolean isSimilarItem(ItemStack item1, ItemStack item2){
        return item1 != null && item2 != null && item1.isSimilar(item2);
    }
}
