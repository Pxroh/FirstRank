package fr.saildrag.FirstRank.button;

import fr.maxlego08.menu.api.button.PaginateButton;
import fr.maxlego08.menu.api.engine.InventoryEngine;
import fr.maxlego08.menu.api.engine.Pagination;
import fr.maxlego08.menu.api.requirement.Permissible;
import fr.saildrag.FirstRank.FirstRank;
import fr.saildrag.FirstRank.core.RequirementManager;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ButtonRequirementsShow extends PaginateButton {
    @Override
    public boolean hasSpecialRender() {
        return true;
    }

    @Override
    public void onRender(Player player, InventoryEngine inventory) {
        List<Permissible> requirementlist = FirstRank.getInstance().getUserManager().getUser(player).getRequirement();
        Pagination<Permissible> pagination = new Pagination<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        pagination.paginate(requirementlist, this.slots.size(), inventory.getPage()).forEach(
                requirement ->
                        RequirementManager.getItemRequirement(this.slots.get(atomicInteger.getAndIncrement()), requirement, player, inventory));
    }

    @Override
    public int getPaginationSize(Player player) {
        return FirstRank.getInstance().getUserManager().getUser(player).getRequirement().size();
    }
}
