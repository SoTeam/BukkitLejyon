package so.team.bukkitlejyon;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import so.team.bukkitlejyon.api.PluginMesajAPI;
import so.team.bukkitlejyon.event.OyunaGirdiðinde;

public class BL extends JavaPlugin implements PluginMessageListener {
	
	public static BL instance;
	String menuTitle = "SonOyuncu Lejyün Menüsü";
	
	//Classlar
		public static PluginMesajAPI pma;
		public static Menu menu;
	//Classlar
		
		
	//Lejyonlar DBsi
		public HashMap<String,Integer> ToplamPuan = new HashMap<String,Integer>();
		public HashMap<String,Integer> AylikPuan = new HashMap<String,Integer>();
		public HashMap<String,Integer> LejyonSeviyesi = new HashMap<String,Integer>();
		public HashMap<String,String> LejyonuKuran = new HashMap<String,String>();
		public HashMap<String,String> MOTD = new HashMap<String,String>();
		public HashMap<String,Long> FaturaTarihi = new HashMap<String,Long>();
	
	//Oyuncular DBsi
		public HashMap<String,String> OyuncuLejyonu = new HashMap<String,String>();
		public HashMap<String,String> OyuncuRütbesi = new HashMap<String,String>();
	    
    public void onEnable() {
    	instance = this;
    	
		//Classlar
			pma = new PluginMesajAPI();
			menu = new Menu();
		//Classlar
    	
    	
	    getServer().getMessenger().registerOutgoingPluginChannel(BL.instance, "BungeeLejyon");
	    getServer().getMessenger().registerIncomingPluginChannel(BL.instance, "BungeeLejyon", this);
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new OyunaGirdiðinde(), this);
        pm.registerEvents(new InventoryClick(), this);
          
    }
    
	@Override
	@EventHandler
	public boolean onCommand(final CommandSender sender, Command command,
			String label, String[] args) {
			if (label.equalsIgnoreCase("bl")) {
				if (sender instanceof Player) {
					if (args.length == 0) {
						pma.oyuncuLejyonuCek(((Player) sender).getPlayer());
						pma.oyuncuRütbesiniÇek(((Player) sender).getPlayer());

				        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
				            public void run(){
				            	//menu.LejyonMenüsünüAç(((Player) sender).getPlayer());
								if (!BL.instance.OyuncuLejyonu.containsKey(sender.getName())){
									pma.yeniLejyon(((Player) sender).getPlayer(), "Test2");
								} else {
									sender.sendMessage("Lejyonun var.");
								}
				            }
				        }
				        , 2L);	
					} else if (args.length == 1) {
						pma.oyuncuLejyonuCek(((Player) sender).getPlayer());
						pma.oyuncuRütbesiniÇek(((Player) sender).getPlayer());
						
				        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
				            public void run(){
								
								menu.LejyonMenüsünüAç(((Player) sender).getPlayer());
				            }
				        }
				        , 2L);	

					}
				}
			}
			return false;
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (channel.equals("BungeeLejyon")) {
	    	ByteArrayDataInput in = ByteStreams.newDataInput(message);
	    	String subchannel = in.readUTF();
	    	if (subchannel.equals("LejyonuCek")) {
	    		String kisi = in.readUTF();
	    		String lejyonu = in.readUTF();
	    			    		
	    		if (lejyonu.equalsIgnoreCase("Yok")){
	    			if (OyuncuLejyonu.containsKey(kisi)){
	    				OyuncuLejyonu.remove(kisi);
	    			}
	    		} else {
	    			OyuncuLejyonu.put(kisi, lejyonu);
	    		}
	    	} else if(subchannel.equals("RütbeCek")) {
	    		String kisi = in.readUTF();
	    		String rütbe = in.readUTF();
	    		
	    		if (rütbe != null){
	    			OyuncuRütbesi.put(kisi, rütbe);
	    		} else {
	    			if (OyuncuRütbesi.containsKey(kisi)){
	    				OyuncuRütbesi.remove(kisi);
	    			}
	    		}
	    	}
		}
	}
}
