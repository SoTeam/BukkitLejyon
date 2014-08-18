package so.team.bukkitlejyon.event;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import so.team.bukkitlejyon.BL;
import so.team.bukkitlejyon.npc.NPC;
import so.team.bukkitlejyon.npc.NPCFactory;
import so.team.bukkitlejyon.npc.NPCInteractEvent;
import so.team.bukkitlejyon.npc.SpawnNPC;

public class SaðTýkNPC implements Listener {

	@EventHandler
	public void NPCyeSaðTýkladýðýnda(final PlayerInteractEntityEvent event) {
		if (event.isCancelled() || event.getPlayer() == null) {
	        return;
	    }
	    if (NPCFactory.isNPC(event.getRightClicked())) {
	        NPC npc = NPCFactory.getNPC(event.getRightClicked());	 
	        NPCInteractEvent e = new NPCInteractEvent(npc, event.getPlayer());	 
	        Bukkit.getPluginManager().callEvent(e);
	        event.setCancelled(e.isCancelled());
	        e.getNpc().setTarget(event.getPlayer());	        
	        if (BL.instance.NPCtipi.get(e.getEntity().getName()).equals("lejyonYoneticisi")){
	        	BL.pma.oyuncuLejyonuCek(event.getPlayer());
	        	BL.pma.oyuncuRütbesiniÇek(event.getPlayer());            
	        	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BL.instance, new Runnable() {
	        		public void run(){                    
	        			BL.menu.LejyonMenüsünüAç(event.getPlayer());
	        		}
	        	}, 2L);
	        } else {
	        	event.getPlayer().sendMessage(ChatColor.GOLD + e.getEntity().getName() + "§r: " + randomMesaj());
	        }
	    }
	}
	
	private String randomMesaj(){
		Random randmsayi = new Random();
		int sayi = randmsayi.nextInt(5);
		String mesaj = null;
		
		if (sayi == 0){
			mesaj = "§eNe istiyorsun ?";
		} else if (sayi == 1){
			mesaj = "§eLejyon kurmak istiyorsan, bir lejyon yöneticisi ile görüþmelisin.";
		} else if (sayi == 2){
			mesaj = "§eÇok iþim var...";
		} else if (sayi == 3){
			mesaj = "§eOradan bakýnca DonduranAtes e mi benziyorum ?";
		} else if (sayi == 4){
			mesaj = ("§eNe bakýyorsun, kavgamý etmek istiyorsun ?");
		} else if (sayi == 5){
			mesaj = ("§eHey sen, saçlarýn lensmi ?");
		}
		return mesaj;
	}
}
