package protocolsupporttab;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.tab.TabAPI;

public class ProtocolSupportTab extends JavaPlugin {

	private BukkitTask task;

	@Override
	public void onEnable() {
		final Config config = new Config(this);
		config.load();
		getCommand("protocolsupporttab").setExecutor(new Commands(config));
		task = Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				TextComponent nextHeader = config.getNextHeader();
				TextComponent nextFooter = config.getNextFooter();
				for (Player player : Bukkit.getOnlinePlayers()) {
					TabAPI.sendHeaderFooter(player, nextHeader, nextFooter);
				}
				Bukkit.getScheduler().runTaskLaterAsynchronously(ProtocolSupportTab.this, this, config.getInterval() * 20);
			}
		}, config.getInterval() * 20);
	}

	@Override
	public void onDisable() {
		task.cancel();
		for (Player player : Bukkit.getOnlinePlayers()) {
			TabAPI.sendHeaderFooter(player, null, null);
		}
	}

}
