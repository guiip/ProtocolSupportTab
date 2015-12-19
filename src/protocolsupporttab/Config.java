package protocolsupporttab;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import protocolsupport.api.chat.components.TextComponent;

public class Config {

	private final ProtocolSupportTab plugin;
	public Config(ProtocolSupportTab plugin) {
		this.plugin = plugin;
	}

	private final Random random = new Random();

	private List<String> headers = new ArrayList<String>();
	private List<String> footers = new ArrayList<String>();
	private boolean isRandom = false;
	private int interval = 10;

	public TextComponent getNextHeader() {
		return fromRaw(getRawNextHeader());
	}

	private int headerIndex = 0;
	private String getRawNextHeader() {
		if (headers.isEmpty()) {
			return null;
		}
		if (!isRandom) {
			return headers.get(headerIndex++ % headers.size());
		} else {
			return headers.get(random.nextInt(headers.size()));
		}
	}

	public TextComponent getNextFooter() {
		return fromRaw(getRawNextFooter());
	}

	private int footerIndex = 0;
	private String getRawNextFooter() {
		if (footers.isEmpty()) {
			return null;
		}
		if (!isRandom) {
			return footers.get(footerIndex++ % footers.size());
		} else {
			return footers.get(random.nextInt(footers.size()));			
		}
	}

	public int getInterval() {
		return interval;
	}

	public void load() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
		headers.clear();
		headers.addAll(config.getStringList("headers"));
		footers.clear();
		footers.addAll(config.getStringList("footers"));
		interval = config.getInt("interval", interval);
		isRandom = config.getBoolean("random");
		save();
	}

	public void save() {
		YamlConfiguration config = new YamlConfiguration();
		config.set("headers", headers);
		config.set("footers", footers);
		config.set("interval", interval);
		config.set("random", isRandom);
		try {
			config.save(new File(plugin.getDataFolder(), "config.yml"));
		} catch (IOException e) {
		}
	}

	private static TextComponent fromRaw(String raw) {
		if (raw == null || raw.isEmpty()) {
			return null;
		} else {
			return new TextComponent(ChatColor.translateAlternateColorCodes('&', raw));
		}
	}

}
