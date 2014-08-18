package so.team.bukkitlejyon;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {
	
    @EventHandler
    public void menuislem(final InventoryClickEvent event){
    	if (event.getInventory().getTitle().equals(BL.instance.menuTitle)){
    		String oyuncu = event.getWhoClicked().getName();
    		
    		event.setCancelled(true);
			
			if (event.getCurrentItem() == null){
				return;
			}
			if (event.getCurrentItem().getType() == Material.AIR){
				return;
			}

   			event.getWhoClicked().closeInventory();
   			
   			if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Lejyon Oluþtur")){
   				if (!BL.instance.OyuncuLejyonu.containsKey(oyuncu)){
   					
   				}
   			}
   			
    	}
    }
}
