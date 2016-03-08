package co.marcin.potionrestrict;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

public enum Message {
	PREFIX,
	NOPERMISSIONS,
	POTIONDISABLED,
	RELOADED;

	public void send(CommandSender sender) {
		sender.sendMessage(PREFIX.get() + get());
	}

	public String get() {
		return Utils.fixColors(PotionRestrict.getInstance().getConfig().getString(getPath()));
	}

	private String getPath() {
		return "message." + StringUtils.replace(name().toLowerCase(), "_", ".");
	}
}
