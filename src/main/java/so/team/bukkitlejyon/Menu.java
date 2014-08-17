package so.team.bukkitlejyon;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu {
	
	   public void LejyonMenüsünüAç(Player p) {    	
	        Inventory inventory = Bukkit.createInventory(p, 9, BL.instance.menuTitle);
	        
	        
			
	        p.openInventory(inventory);	        
	    }
	   
		@SuppressWarnings("deprecation")
		public ItemStack item(int itemid,int adet,String ismi,String lore,String lore2,int durability,int shortu){
			 ItemStack spawnItem;
			 if (shortu >= 0){
				 spawnItem = new ItemStack(itemid, adet, (short) shortu);
			 } else {
				 spawnItem = new ItemStack(itemid,adet);
			 }
			 if (durability >= 0){
				 spawnItem.setDurability((short) durability);
			 }
			 ItemMeta im =  spawnItem.getItemMeta();
			 if (ismi != null){
				 im.setDisplayName(ismi);
			 }
			 if (lore2 != null && lore != null){
				 im.setLore(Arrays.asList(lore,lore2));
			 } else if (lore2 == null && lore != null){
				 im.setLore(Arrays.asList(lore));
			 }
			 spawnItem.setItemMeta(im);
			 
			 return spawnItem;
		 }

}
