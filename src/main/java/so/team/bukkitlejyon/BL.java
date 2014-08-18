package so.team.bukkitlejyon;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import so.team.bukkitlejyon.event.SaðTýkNPC;
import so.team.bukkitlejyon.npc.NPCFactory;
import so.team.bukkitlejyon.npc.SpawnNPC;

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
		
	//NPC datasý
		public HashMap<String,String> NPCtipi = new HashMap<String,String>();
//		public HashMap<String,String> NPCkafasý = new HashMap<String,String>();
		public HashMap<String,Location> NPCkoordinatý = new HashMap<String,Location>();
		
	    
    public void onEnable() {
    	instance = this;

		//Classlar
			pma = new PluginMesajAPI();
			menu = new Menu();
		//Classlar
    	
		if (getConfig().contains("NPC.1")){
			npcleriYükle();
		}
			
	    getServer().getMessenger().registerOutgoingPluginChannel(BL.instance, "BungeeLejyon");
	    getServer().getMessenger().registerIncomingPluginChannel(BL.instance, "BungeeLejyon", this);
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new SaðTýkNPC(), this);
        pm.registerEvents(new InventoryClick(), this);
        
    }
    
	public void npcleriYükle(){
		for (int i = 1;;){
			if (getConfig().contains("NPC." + i)){
	        	NPCtipi.put(getConfig().getString("NPC."+i+".ismi"), getConfig().getString("NPC."+i+".tipi"));
	    //    	NPCkafasý.put(getConfig().getString("NPC."+i+".ismi"), getConfig().getString("NPC."+i+".kafasi"));
	        	NPCkoordinatý.put(getConfig().getString("NPC."+i+".ismi"), Metotlar.str2loc(getConfig().getString("NPC."+i+".koordinati")));
				SpawnNPC.spawnNPC(getConfig().getString("NPC."+i+".ismi"));
	        	i++;
			} else {
				return;
			}
		}
	}
	
	public int müsaitSayý(){
		for (int i = 1;;){
			if (getConfig().contains("NPC." + i)){
	        	i++;
			} else {
				return i;
			}
		}
	}
    
	@Override
	@EventHandler
	public boolean onCommand(final CommandSender sender, Command command,
			String label, String[] args) {
			if (label.equalsIgnoreCase("npc")) {
				if (sender instanceof Player) {
					if (args.length == 0) {
						sender.sendMessage("§e/npc yeni <ismi> [<lejyonYoneticisi | lejyonMarketi>] §r- Yeni npc yaratýr.");
					} else if (args.length == 1){
						if (args[0].toLowerCase().equalsIgnoreCase("reload")){
							BL.instance.reloadConfig();
						}
						if (args[0].toLowerCase().equalsIgnoreCase("respawn")){
							NPCFactory.despawnAll();
							NPCtipi.clear();
				//			NPCkafasý.clear();
							NPCkoordinatý.clear();
							npcleriYükle();
						}
						if (args[0].toLowerCase().equalsIgnoreCase("despawn")){
							NPCFactory.despawnAll();
						}			
					} else if (args.length == 2){
						if (args[0].toLowerCase().equalsIgnoreCase("yeni")){
							int sýra = müsaitSayý();
							getConfig().set("NPC."+sýra+".ismi", args[1]);
							getConfig().set("NPC."+sýra+".tipi", "diger");
				//			getConfig().set("NPC."+sýra+".kafasi", args[2]);
							getConfig().set("NPC."+sýra+".koordinati", Metotlar.loc2str(((Player) sender).getLocation()));
							saveConfig();
				//			NPCkafasý.put(args[1], args[2]);
							NPCkoordinatý.put(args[1], ((Player) sender).getLocation());
							NPCtipi.put(args[1], "diger");
							SpawnNPC.spawnNPC(args[1]);
							sender.sendMessage("§6Yeni npc eklendi, özellikleri: §eÝsmi: §c" + args[1] + " §eTipi: §cdiger");
						}
					} else if (args.length == 3) { //npc yeni DonduranAtes lejyonYonetimi
						if (args[0].toLowerCase().equalsIgnoreCase("yeni")){
							if (!(args[2].equalsIgnoreCase("lejyonYoneticisi") || args[2].equalsIgnoreCase("lejyonMarketi"))){
								sender.sendMessage("§cNpc tipi lejyonYoneticisi yada lejyonMarketi olmalýdýr.");
								return false;
							}
							int sýra = müsaitSayý();
							getConfig().set("NPC."+sýra+".ismi", args[1]);
							getConfig().set("NPC."+sýra+".tipi", args[2]);
				//			getConfig().set("NPC."+sýra+".kafasi", args[2]);
							getConfig().set("NPC."+sýra+".koordinati", Metotlar.loc2str(((Player) sender).getLocation()));
							saveConfig();
				//			NPCkafasý.put(args[1], args[2]);
							NPCkoordinatý.put(args[1], ((Player) sender).getLocation());
							NPCtipi.put(args[1], args[2]);
							SpawnNPC.spawnNPC(args[1]);
							sender.sendMessage("§6Yeni npc eklendi, özellikleri: §eÝsmi: §c" + args[1] + " §eTipi: §c" + args[2]);
						}
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
