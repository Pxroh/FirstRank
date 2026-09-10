package fr.saildrag.FirstRank.permissible.action;

import fr.maxlego08.menu.api.loader.ActionLoader;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.TypedMapAccessor;
import fr.saildrag.FirstRank.FirstRank;
import fr.saildrag.FirstRank.permissible.requirement.virtualStorage.*;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class StockAddLoader extends ActionLoader {
    private final FirstRank plugin;
    public StockAddLoader(FirstRank plugin){
        this.plugin = plugin;
    }

    @Override
    public List<String> getKeys() {
        return Collections.singletonList("itemquestadd");
    }

    @Override
    public Action load(String path, TypedMapAccessor accessor, File file) {
        String virtual_type = accessor.getString("virtual_type");

        IVirtualPermissible virtualPermissible = switch (virtual_type) {
            case "currency":
                yield new VirtualCurrency();
            case "material":
            default:
                yield new VirtualMaterial();
        };
        virtualPermissible.load(path, accessor, file);

        return new StockAddAction(plugin,virtualPermissible, accessor.getString("quest_id"));
    }
}