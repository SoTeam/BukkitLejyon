/**
 * 
 */
package so.team.bukkitlejyon;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import so.team.bukkitlejyon.event.OyunaGirdiðinde;

/**
 * @authors IRFN, DonduranAtes, Speaw
 *
 */
public class BL extends JavaPlugin {
	
    public void onDisable() {}
    
    public void onEnable() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new OyunaGirdiðinde(), this);
          
    }

}
