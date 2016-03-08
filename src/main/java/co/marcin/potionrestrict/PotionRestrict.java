package co.marcin.potionrestrict;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PotionRestrict extends JavaPlugin implements Listener {
	private final Map<Material, Boolean> typeConfiguration = new HashMap<>();
	private static PotionRestrict instance;

	@Override
	public void onEnable() {
		instance = this;

		if(!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
			info("Created default config file");
		}

		loadConfiguration();

		getCommand("potionrestrict").setExecutor(new CommandPotionRestrict());
		getServer().getPluginManager().registerEvents(this, this);

		info("v" + getDescription().getVersion() + " Enabled");
	}

	@Override
	public void onDisable() {
		info("v" + getDescription().getVersion() + " Disabled");
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getItem() == null) {
			return;
		}

		Material type = event.getItem().getType();

		if(typeConfiguration.containsKey(type) && !typeConfiguration.get(type) && !Permission.BYPASS.has(event.getPlayer())) {
			Message.POTIONDISABLED.send(event.getPlayer());
			event.setCancelled(true);
		}
	}

	public static PotionRestrict getInstance() {
		return instance;
	}

	public static void info(String message) {
		Bukkit.getLogger().info("[PotionRestrict] " + message);
	}

	private class CommandPotionRestrict implements CommandExecutor {
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if(args.length == 0) {
				sender.sendMessage(Utils.fixColors(Message.PREFIX.get() + "Marcin (CTRL) Wieczorek"));
				sender.sendMessage(Utils.fixColors(Message.PREFIX.get() + "http://marcin.co/"));
			}
			else {
				switch(args[0].toLowerCase()) {
					case "reload":
						if(!Permission.RELOAD.has(sender)) {
							Message.NOPERMISSIONS.send(sender);
							return true;
						}

						loadConfiguration();
						Message.RELOADED.send(sender);
						break;
				}
			}

			return true;
		}
	}

	private void loadConfiguration() {
		reloadConfig();

		typeConfiguration.clear();
		typeConfiguration.put(Material.POTION, getConfig().getBoolean("potion.normal"));
		typeConfiguration.put(Material.SPLASH_POTION, getConfig().getBoolean("potion.splash"));
		typeConfiguration.put(Material.LINGERING_POTION, getConfig().getBoolean("potion.lingering"));
	}
}
