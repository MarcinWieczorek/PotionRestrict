package co.marcin.potionrestrict;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

public enum Permission {
	RELOAD,
	BYPASS;

	public boolean has(CommandSender sender) {
		return sender.hasPermission(getPath());
	}

	private String getPath() {
		return "potionrestrict." + StringUtils.replace(name().toLowerCase(), "_", ".");
	}
}