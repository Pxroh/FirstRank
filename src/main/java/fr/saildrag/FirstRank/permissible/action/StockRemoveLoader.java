package fr.saildrag.FirstRank.permissible.action;

import fr.maxlego08.menu.api.loader.ActionLoader;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.TypedMapAccessor;
import fr.saildrag.FirstRank.FirstRank;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class StockRemoveLoader extends ActionLoader {
    private final FirstRank plugin;

    public StockRemoveLoader(FirstRank plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getKeys() {
        return Collections.singletonList("itemquestremove");
    }

    @Override
    public Action load(String path, TypedMapAccessor accessor, File file) {
        String quest_id = accessor.getString("quest_id");

        return new StockRemoveAction(plugin, quest_id);
    }
}