package fr.saildrag.FirstRank.permissible.requirement;

import fr.maxlego08.menu.api.ButtonManager;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.requirement.Permissible;
import fr.maxlego08.menu.api.utils.TypedMapAccessor;
import fr.saildrag.FirstRank.FirstRank;
import fr.saildrag.FirstRank.core.ClickType;
import fr.saildrag.FirstRank.core.RequirementManager;
import fr.saildrag.FirstRank.permissible.ZPermissibleLoader;
import fr.saildrag.FirstRank.permissible.action.StockAddAction;
import fr.saildrag.FirstRank.permissible.action.StockAddLoader;
import fr.saildrag.FirstRank.permissible.requirement.virtualStorage.IVirtualPermissible;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VirtualStoragePermissibleLoader extends ZPermissibleLoader {
    private final FirstRank plugin;
    private final ButtonManager buttonManager;

    public VirtualStoragePermissibleLoader(FirstRank plugin, ButtonManager buttonManager) {
        super("virtualStorage");
        this.plugin = plugin;
        this.buttonManager = buttonManager;
    }

    @Override
    public Permissible load(String path, TypedMapAccessor accessor, File file) {
        String quest_id = accessor.getString("quest_id");

        List<Action> leftClickActions = loadAction(buttonManager, accessor, "leftClickActions", path, file);
        List<Action> rightClickActions = loadAction(buttonManager, accessor, "rightClickActions", path, file);
        List<Action> middleClickActions = loadAction(buttonManager, accessor, "middleClickActions", path, file);

        StockAddAction stockActionRaw = (StockAddAction) new StockAddLoader(this.plugin).load(path, accessor, file);
        IVirtualPermissible stockAction = stockActionRaw.getVirtualPermissible();

        Map<String, ClickType> clickType = new HashMap<>();
        String left_count = accessor.getString(
                "left_click",
                RequirementManager.getDefault_clicks("left").getValue()
        );
        clickType.put("left", new ClickType(
                "left",
                left_count,
                RequirementManager.getDefault_clicks("left").getMessagePositive(),
                RequirementManager.getDefault_clicks("left").getMessageNegative()
        ));

        leftClickActions.add(getAction(path, accessor, file, left_count));


        String right_count = accessor.getString(
                "right_click",
                RequirementManager.getDefault_clicks("right").getValue()
        );
        clickType.put("right", new ClickType(
                "right",
                right_count,
                RequirementManager.getDefault_clicks("right").getMessagePositive(),
                RequirementManager.getDefault_clicks("right").getMessageNegative()
        ));
        rightClickActions.add(getAction(path, accessor, file, right_count));


        String middle_count = accessor.getString(
                "click.middle",
                RequirementManager.getDefault_clicks("middle").getValue()
        );
        clickType.put("middle", new ClickType(
                "middle",
                left_count,
                RequirementManager.getDefault_clicks("middle").getMessagePositive(),
                RequirementManager.getDefault_clicks("middle").getMessageNegative()
        ));
        middleClickActions.add(getAction(path, accessor, file, middle_count));

        List<Action> denyActions = loadAction(buttonManager, accessor, "deny", path, file);
        List<Action> successActions = loadAction(buttonManager, accessor, "success", path, file);

        return new VirtualStoragePermissible(
                this.plugin,
                quest_id,
                stockAction,
                clickType,
                denyActions,
                successActions,
                leftClickActions,
                rightClickActions,
                middleClickActions
        );

    }

    protected Action getAction(String path, TypedMapAccessor accessor, File file, String config_count){
        StockAddAction stockActionRaw = (StockAddAction) new StockAddLoader(this.plugin).load(path, accessor, file);
        IVirtualPermissible stockAction = stockActionRaw.getVirtualPermissible();

        int count;
        if ("all".equalsIgnoreCase(config_count)) {
            count = stockAction.getQuest_count();
        } else {
            try {
                count = Integer.parseInt(config_count);
            } catch (NumberFormatException e) {
                count = 0;
            }
        }

        stockAction.load(path, accessor, file);
        stockAction.setCount(count);
        return stockActionRaw;
    }
}
