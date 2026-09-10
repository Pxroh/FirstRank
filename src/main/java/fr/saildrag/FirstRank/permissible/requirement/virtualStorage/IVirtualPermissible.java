package fr.saildrag.FirstRank.permissible.requirement.virtualStorage;

import fr.maxlego08.menu.api.utils.TypedMapAccessor;
import fr.saildrag.FirstRank.dataBase.UserStorage;
import org.bukkit.entity.Player;

import java.io.File;

public interface IVirtualPermissible {
    void load(String path, TypedMapAccessor accessor, File file);

    int execute(Player player, UserStorage userStorage);

    void setCount(int i);

    int getCount();

    void setQuestCount(int i);

    int getQuest_count();

    void setId(String id);

    String getId();
}
