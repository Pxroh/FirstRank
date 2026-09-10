package fr.saildrag.FirstRank.permissible.requirement.virtualStorage;

import fr.maxlego08.menu.api.utils.TypedMapAccessor;
import fr.saildrag.FirstRank.FirstRank;
import fr.saildrag.FirstRank.dataBase.UserStorage;
import fr.traqueur.currencies.Currencies;
import org.bukkit.entity.Player;

import java.io.File;
import java.math.BigDecimal;

public class VirtualCurrency extends VirtualPermissible {
    private Currencies currencies;
    private String economyName;

    public VirtualCurrency() {
        super("currency");
    }

    @Override
    public void load(String path, TypedMapAccessor accessor, File file) {
        super.load(path, accessor, file);
        this.currencies = Currencies.valueOf(accessor.getString("currency", Currencies.VAULT.name()).toUpperCase());
        this.economyName = accessor.getString("economy", null);
        this.economyName = this.economyName == null ? "default" : this.economyName;
        String id = this.economyName == "default" ? "" : "-" + this.economyName;
        setId(this.currencies + id);
    }

    @Override
    public int execute(Player player, UserStorage userStorage) {
        int itemsToGain = Math.min(this.quest_count - userStorage.getValue(), count);
        int currentAmount = this.currencies.getBalance(player, this.economyName).intValue();
        int itemsToRemove = Math.min(currentAmount, itemsToGain);

        this.currencies.withdraw(player, new BigDecimal(itemsToRemove), this.economyName,"VirtualCurrency");
        return itemsToRemove;
    }
}
