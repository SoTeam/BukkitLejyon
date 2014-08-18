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

public class Sa�T�kNPC implements Listener {

	@EventHandler
	public void NPCyeSa�T�klad���nda(final PlayerInteractEntityEvent event) {
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
	        	BL.pma.oyuncuR�tbesini�ek(event.getPlayer());            
	        	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BL.instance, new Runnable() {
	        		public void run(){                    
	        			BL.menu.LejyonMen�s�n�A�(event.getPlayer());
	        		}
	        	}, 2L);
	        } else {
	        	event.getPlayer().sendMessage(ChatColor.GOLD + e.getEntity().getName() + "�r: " + randomMesaj());
	        }
	    }
	}
	
	private String randomMesaj(){
		Random randmsayi = new Random();
		int sayi = randmsayi.nextInt(5);
		String mesaj = null;
		
		if (sayi == 0){
			mesaj = "�eNe istiyorsun ?";
		} else if (sayi == 1){
			mesaj = "�eLejyon kurmak istiyorsan, bir lejyon y�neticisi ile g�r��melisin.";
		} else if (sayi == 2){
			mesaj = "�e�ok i�im var...";
		} else if (sayi == 3){
			mesaj = "�eOradan bak�nca DonduranAtes e mi benziyorum ?";
		} else if (sayi == 4){
			mesaj = ("�eNe bak�yorsun, kavgam� etmek istiyorsun ?");
		} else if (sayi == 5){
			mesaj = ("�eHey sen, sa�lar�n lensmi ?");
		}
		return mesaj;
	}
}
