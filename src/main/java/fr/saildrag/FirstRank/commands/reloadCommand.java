package fr.saildrag.FirstRank.commands;

import fr.saildrag.library.commands.BaseCommand;
import fr.saildrag.library.commands.CommandHandle;
import fr.saildrag.FirstRank.lists.Message;
import fr.saildrag.FirstRank.FirstRank;

public class reloadCommand extends BaseCommand<FirstRank> {
    public reloadCommand(FirstRank plugin, String id) {
		super(plugin, id);
    }
	@Override
	public String command(CommandHandle handle) {
		getPlugin().loadConfig();
		return Message.COMMAND_RELOAD.toString();
	}
}
