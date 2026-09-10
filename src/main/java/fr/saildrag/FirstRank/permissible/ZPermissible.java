package fr.saildrag.FirstRank.permissible;

import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.requirement.Permissible;

import java.util.List;

public abstract class ZPermissible extends Permissible {

    private final List<Action> leftClickActions;
    private final List<Action> rightClickActions;
    private final List<Action> middleClickActions;

    public ZPermissible(List<Action> denyActions, List<Action> successActions, List<Action> leftClickActions, List<Action> rightClickActions, List<Action> middleClickActions) {
        super(denyActions, successActions);
        this.leftClickActions = leftClickActions;
        this.rightClickActions = rightClickActions;
        this.middleClickActions = middleClickActions;
    }

    public List<Action> getLeftClickActions() {
        return this.leftClickActions;
    }
    public List<Action> getRightClickActions() {
        return this.rightClickActions;
    }
    public List<Action> getMiddleClickActions() {
        return this.middleClickActions;
    }
}