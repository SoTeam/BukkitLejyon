package so.team.bukkitlejyon.npc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import so.team.bukkitlejyon.BL;

public class SpawnNPC {
	
	
 /*   public static ItemStack oyuncuKafasý(String isim){
    	ItemStack kafa = new ItemStack(Material.SKULL_ITEM,1, (short)3);
    	SkullMeta kafaMeta = (SkullMeta) kafa.getItemMeta();
    	
    	kafaMeta.setOwner(isim);
    	kafa.setItemMeta(kafaMeta);
    	
    	return kafa;
    }
 */
	
	public static void spawnNPC(String NPCismi){
    	final NPCFactory factory = new NPCFactory(BL.instance);
    	final Location location = BL.instance.NPCkoordinatý.get(NPCismi);
    	final NPC npc = factory.spawnHumanNPC(location, new NPCProfile(NPCismi));
    	npc.setYaw(location.getYaw());
    	npc.setEntityCollision(false);
    	if (BL.instance.NPCtipi.get(NPCismi).equalsIgnoreCase("lejyonYoneticisi")){
    		npc.setEquipment(EquipmentSlot.HELMET, new ItemStack(Material.GOLD_HELMET));
    		npc.setEquipment(EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD));
    		npc.setEquipment(EquipmentSlot.BOOTS, new ItemStack(Material.IRON_BOOTS));
    		npc.setEquipment(EquipmentSlot.CHESTPLATE, new ItemStack(Material.GOLD_CHESTPLATE));
    		npc.setEquipment(EquipmentSlot.LEGGINGS, new ItemStack(Material.IRON_LEGGINGS));
    	} else if (BL.instance.NPCtipi.get(NPCismi).equalsIgnoreCase("lejyonMarketi")){
    		npc.setEquipment(EquipmentSlot.HELMET, new ItemStack(Material.IRON_HELMET));
    		npc.setEquipment(EquipmentSlot.HAND, new ItemStack(Material.DIAMOND_SWORD));
    		npc.setEquipment(EquipmentSlot.BOOTS, new ItemStack(Material.DIAMOND_BOOTS));
    		npc.setEquipment(EquipmentSlot.CHESTPLATE, new ItemStack(Material.DIAMOND_CHESTPLATE));
    		npc.setEquipment(EquipmentSlot.LEGGINGS, new ItemStack(Material.DIAMOND_LEGGINGS));
    	} else {
    		npc.setEquipment(EquipmentSlot.HELMET, new ItemStack(Material.DIAMOND_HELMET));
    		npc.setEquipment(EquipmentSlot.HAND, new ItemStack(Material.DIAMOND_SWORD));
    		npc.setEquipment(EquipmentSlot.BOOTS, new ItemStack(Material.DIAMOND_BOOTS));
    		npc.setEquipment(EquipmentSlot.CHESTPLATE, new ItemStack(Material.DIAMOND_CHESTPLATE));
    		npc.setEquipment(EquipmentSlot.LEGGINGS, new ItemStack(Material.DIAMOND_LEGGINGS));
    	}
    	
    	
    /*	final List<Entity> yakýndakiEntityler = npc.getBukkitEntity().getNearbyEntities(5.0D, 5.0D, 5.0D);
       	Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable(){
    		@Override
    		public void run(){
    		yakýndakiEntityler.clear();
    		yakýndakiEntityler.addAll(npc.getBukkitEntity().getNearbyEntities(5.0D, 5.0D, 5.0D));
    		npc.setTarget(yakýndakiEntityler.get(0));
    		}
    		}, 2L, 30L);
    		
    		*/
		
	}

}
