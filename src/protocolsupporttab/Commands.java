package protocolsupporttab;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class Commands implements CommandExecutor, TabCompleter {

	private final Config config;
	public Commands(Config config) {
		this.config = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("protocolsupporttab.admin")) {
			sender.sendMessage(ChatColor.RED + "You have no power here!");
			return true;
		}
		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			config.load();
			sender.sendMessage(ChatColor.YELLOW + "Config reload");
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (args[0].isEmpty()) {
			return Arrays.asList("reload");
		}
		if ("reload".startsWith(args[0])) {
			return Collections.singletonList("reload");
		}
		return Collections.emptyList();
	}

}
