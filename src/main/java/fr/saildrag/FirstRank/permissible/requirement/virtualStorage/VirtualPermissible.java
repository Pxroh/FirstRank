package fr.saildrag.FirstRank.permissible.requirement.virtualStorage;

import fr.maxlego08.menu.api.utils.TypedMapAccessor;
import fr.saildrag.FirstRank.dataBase.UserStorage;
import org.bukkit.entity.Player;

import java.io.File;

public class VirtualPermissible implements IVirtualPermissible {
    public int quest_count = 0;
    public int count = 0;
    public String id;

    public VirtualPermissible(String id) {
        this.id = id;
    }

    @Override
    public void load(String path, TypedMapAccessor accessor, File file) {
        setQuestCount(accessor.getInt("quest_count"));
        setCount(accessor.getInt("count", this.quest_count));
    }

    @Override
    public int execute(Player player, UserStorage userStorage) {
        return 0;
    }

    @Override
    public void setCount(int i) {
        this.count = i;
    }

    @Override
    public int getCount(){
        return this.count;
    }

    @Override
    public void setQuestCount(int i) {
        this.quest_count = i;
    }

    @Override
    public int getQuest_count() {
        return quest_count;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
