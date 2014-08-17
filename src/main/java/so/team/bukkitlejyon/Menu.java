package so.team.bukkitlejyon;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Menu {
	
	   public void LejyonMenüsünüAç(Player p) {    	
	        Inventory inventory = Bukkit.createInventory(p, 45, BL.instance.menuTitle);
			
	        p.openInventory(inventory);	        
	    }

}
