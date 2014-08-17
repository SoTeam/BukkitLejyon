package so.team.bukkitlejyon;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import so.team.bukkitlejyon.event.OyunaGirdiðinde;

public class BL extends JavaPlugin {
	
	public static BL instance;
	String menuTitle = "SonOyuncu Lejyün Menüsü";
	    
    public void onEnable() {
    	instance = this;
    	
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new OyunaGirdiðinde(), this);
          
    }
    
    

}
