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
	        
	        if (!BL.instance.OyuncuLejyonu.containsKey(p.getName())){
		        inventory.setItem(0, new ItemStack(item(339, 1, "Lejyon Oluþtur", "DÝKKAT: 3 aylýk lejyon harcý 20 So Kredidir.", "Hesabýnýzda 20 So Kredi varsa tahsil edilerek lejyonunuz","3 aylýk süre ile oluþturulacaktýr.", -1, -1)));
	        } else {
	        	if (BL.instance.OyuncuRütbesi.get(p.getName()).equalsIgnoreCase("Tuðgeneral")){
			        inventory.setItem(0, new ItemStack(item(339, 1, "Lejyon adýný deðiþtir", null, null,null, -1, -1)));
			        inventory.setItem(1, new ItemStack(item(339, 1, "Lejyon Tuðgeneral rütbelerini aktar", null, null,null, -1, -1)));
			        inventory.setItem(2, new ItemStack(item(339, 1, "Lejyon üyelerinin rütbesini ayarlama", null, null,null, -1, -1)));
			        inventory.setItem(8, new ItemStack(item(152, 1, "Lejyonu kapat", null, null,null, -1, -1)));
	        	} else {
	        		p.sendMessage("Tuðgeneral olmalýsýn.");
	        		return;
	        	}
	        }
	        
	        p.openInventory(inventory);	        
	    }
	   
		@SuppressWarnings("deprecation")
		public ItemStack item(int itemid,int adet,String ismi,String lore,String lore2,String lore3,int durability,int shortu){
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
			 
			 if (lore3 != null){
				 im.setLore(Arrays.asList(lore,lore2,lore3));
			 } else if (lore2 != null){
				 im.setLore(Arrays.asList(lore,lore2));
			 } else if (lore != null){
				 im.setLore(Arrays.asList(lore));
			 }

			 spawnItem.setItemMeta(im);
			 
			 return spawnItem;
		 }

}
