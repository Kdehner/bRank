package net.kdehner.bRank;

import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class bRank extends JavaPlugin {
	
	public static final Logger log = Logger.getLogger("Minecraft");
	public static Permission perms = null;
	
	@Override
	public void onEnable() {
		if (!setupPermissions()) {
			log.info(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		log.info(String.format("[%s] Enabled Version %s", getDescription().getName(), getDescription().getVersion()));
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	@Override
	public void onDisable() {
		log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));

	}
	
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			String code = getConfig().getString("code");
			String rank1 = getConfig().getString("rank1");
			String rank2 = getConfig().getString("rank2");
			
		if(perms.has(player, "bRank.guest")){
			
			if((cmd.getName().equalsIgnoreCase("regular")) && (args.length == 1)){
				
				if(code.equalsIgnoreCase(args[0])) {
					perms.playerRemoveGroup(player, rank1);
					perms.playerAddGroup(player, rank2);
					sender.sendMessage("You have been promoted to Regular");
					return true;
			}
				else{
				sender.sendMessage("Incorrect Code");
				return true;
			}
		}
		
			}
		
		}
		
		else {
			sender.sendMessage("You must be a player!");
			return true;
		}
		
		
		if((cmd.getName().equalsIgnoreCase("bRank")) && (args.length == 1)) {
			String reload = "reload";
			if(reload.equalsIgnoreCase(args[0])){
				this.reloadConfig();
			}
		}
		return false;
	}

}
