package co.marcin.potionrestrict;

import org.bukkit.ChatColor;

public class Utils {
	public static String fixColors(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
