package so.team.bukkitlejyon;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import so.team.bukkitlejyon.event.OyunaGirdi�inde;

public class BL extends JavaPlugin {
	
	public static BL instance;
	String menuTitle = "SonOyuncu Lejy�n Men�s�";
	    
    public void onEnable() {
    	instance = this;
    	
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new OyunaGirdi�inde(), this);
          
    }
    
    

}
