package fr.saildrag.FirstRank.permissible;

import fr.maxlego08.menu.api.ButtonManager;
import fr.maxlego08.menu.api.loader.PermissibleLoader;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.TypedMapAccessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ZPermissibleLoader extends PermissibleLoader {

    public ZPermissibleLoader(String key) {
        super(key);
    }

    protected List<Action> loadAction(ButtonManager buttonManager, TypedMapAccessor accessor, String key, String path, File file) {
        return buttonManager.loadActions((List<Map<String, Object>>) accessor.getObject(key, new ArrayList<Map<String, Object>>()), path, file);
    }

}